package app.com.patricksdeliveryboy.notification

import android.content.Context
import android.content.Intent
import android.util.Log
import app.com.patricksdeliveryboy.ReferenceIds.Companion.ReferenceId
import app.com.patricksdeliveryboy.home.HomeActivity
import app.com.patricksdeliveryboy.models.OneSignalResponse
import app.com.patricksdeliveryboy.models.OnesignalPayloadResponse
import app.com.patricksdeliveryboy.sideMenu.SideMenuPagesActivity
import app.com.patricksdeliveryboy.utility.JsonUtils
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal

class NotificationOpenHandler(var context: Context) : OneSignal.OSNotificationOpenedHandler {
    override fun notificationOpened(result: OSNotificationOpenedResult?) {
        try {
        //    val response : OneSignalResponse = JsonUtils.getAsObject(result?.notification?.rawPayload!!)
        //    val payloadResponse : OnesignalPayloadResponse = JsonUtils.getAsObject(response.custom!!)

            val intent : Intent = Intent(context, SideMenuPagesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
          //  intent.putExtra("from_notification", payloadResponse.A?.referenceId)
             intent.putExtra("from_notification", "1234sdg")
            context.startActivity(intent)

            ReferenceId = "1234sdg"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}