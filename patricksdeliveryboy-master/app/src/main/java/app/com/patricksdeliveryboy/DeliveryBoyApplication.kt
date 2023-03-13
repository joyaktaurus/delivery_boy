package app.com.patricksdeliveryboy

import android.app.Application
import app.com.patricksdeliveryboy.notification.NotificationOpenHandler
import app.com.patricksdeliveryboy.utility.OneSignalConstants
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DeliveryBoyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(OneSignalConstants.APP_ID)
        OneSignal.setNotificationOpenedHandler(NotificationOpenHandler(this))
    }
}