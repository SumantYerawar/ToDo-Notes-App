package com.sumant.todonotesapp.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sumant.todonotesapp.R

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        setupNotificationMessage(p0.notification?.body)
    }

    private fun setupNotificationMessage(body: String?) {
        val channelId = "Todo_Id"
        val ringtoneId = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder =  NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("ToDo Notes")
                .setContentText(body)
                .setSound(ringtoneId)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, "ToDo-Notes", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
}