package com.jdccmobile.memento.core

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.jdccmobile.memento.R
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.ui.views.SplashActivity
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.NOTIFICATION_ID

class AlarmNotification: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        createDailyNotification(context)
    }

    private fun createDailyNotification(context: Context){
        val intent = Intent(context, QuotesModel::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val dailyNotification = NotificationCompat.Builder(context,
            SplashActivity.MY_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Nueva cita diaria")
            .setContentText("Descubre que nueva enseñanza aprenderás de los filosofos de la antigüedad")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, dailyNotification)
    }

}