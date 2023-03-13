package app.com.patricksdeliveryboy.worker

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import app.com.patricksdeliveryboy.R
import com.inmenzo.patrics.exception.UnauthorizedException
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket
import io.socket.emitter.Emitter
import javax.inject.Inject

import android.os.Looper

import android.os.Binder

import androidx.annotation.RequiresApi

import com.google.android.gms.location.*


@AndroidEntryPoint
class LocationService : Service() {

    private val TAG: String = this::class.java.simpleName

    /**
     * The name of the channel for notifications.
     */
    private val CHANNEL_ID = "channel_01"

    private val mBinder: IBinder = LocalBinder()

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2

    /**
     * The identifier for the notification displayed for the foreground service.
     */
    private val NOTIFICATION_ID = 12345678

    /**
     * Used to check whether the bound activity has really gone away and not unbound as part of an
     * orientation change. We create a foreground service notification only if the former takes
     * place.
     */
    private var mChangingConfiguration = false

    private var mNotificationManager: NotificationManager? = null

    /**
     * Contains parameters used by [com.google.android.gms.location.FusedLocationProviderApi].
     */
    private var mLocationRequest: LocationRequest? = null

    /**
     * Provides access to the Fused Location Provider API.
     */
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    /**
     * Callback for changes in location.
     */
    private var mLocationCallback: LocationCallback? = null


    /**
     * The current location.
     */
    private var mLocation: Location? = null

    /**
     * Class used for the client Binder.  Since this service runs in the same process as its
     * clients, we don't need to deal with IPC.
     */
    internal class LocalBinder : Binder() {
        fun getService(): LocationService {
            return LocationService()
        }
    }

    @Inject
    lateinit var socket: Socket

    private fun createNotification(title: String, description: String) {
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(false)
            .setOngoing(true)
        val notification = notificationBuilder.build()
        mNotificationManager?.notify(NOTIFICATION_ID, notification)
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onBind(intent: Intent?): IBinder {
        mChangingConfiguration = false
        return this.mBinder
    }

    override fun onCreate() {
        super.onCreate()
        createSocketConnection()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                onNewLocation(locationResult.lastLocation)
            }
        }

        createLocationRequest()
        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(app.com.patricksdeliveryboy.R.string.app_name)
            createNotificationChannel(CHANNEL_ID, name)
        }
        try {
            mFusedLocationClient!!.requestLocationUpdates(
                mLocationRequest!!,
                mLocationCallback!!, Looper.myLooper()!!
            )
        } catch (unlikely: SecurityException) {
            Log.e(TAG, "Lost location permission. Could not request updates. $unlikely")
        }
        createNotification("Location", "Collecting your location")
    }

    private val onConnect: Emitter.Listener = Emitter.Listener {
        print(it)
    }

    private val onAuthError: Emitter.Listener = Emitter.Listener {
        throw UnauthorizedException()
    }

    private val onFeedBack: Emitter.Listener = Emitter.Listener {
        print(it)
    }
    private val onConnectError: Emitter.Listener = Emitter.Listener {
        print(it)
    }

    override fun onDestroy() {
        super.onDestroy()
        mFusedLocationClient?.removeLocationUpdates(mLocationCallback!!);
        socket.disconnect()
        socket.off(Socket.EVENT_CONNECT, onConnect)
        socket.off(Socket.EVENT_DISCONNECT, onConnectError)
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.off("feedback", onFeedBack)
        socket.off("authError", onAuthError)
    }

    private fun createSocketConnection() {
        socket.on(Socket.EVENT_CONNECT, onConnect)
        socket.on(Socket.EVENT_DISCONNECT, onConnectError)
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.on("feedback", onFeedBack)
        socket.on("authError", onAuthError)
        socket.connect()
    }

    private fun onNewLocation(location: Location) {
        Log.i(TAG, "New location: $location")
        mLocation = location
        if (!socket.connected()){
            socket.connect()
        } else {
            socket.emit(
                "onLocationChange",
                "{\"lat\": ${mLocation!!.latitude},\"lng\": ${mLocation!!.longitude}}"
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    /**
     * Sets the location request parameters.
     */
    private fun createLocationRequest() {
        mLocationRequest = LocationRequest.create()
        mLocationRequest!!.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest!!.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
}