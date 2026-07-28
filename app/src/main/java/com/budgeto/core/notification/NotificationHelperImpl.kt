package com.budgeto.core.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.budgeto.core.logger.ReportLogger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val reportLogger: ReportLogger
) : NotificationHelper {

    override fun ensureChannel(
        channelId: String,
        channelName: String,
        importance: Int,
        description: String
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(channelId, channelName, importance).apply {
            this.description = description
        }
        context.getSystemService(NotificationManager::class.java)
            ?.createNotificationChannel(channel)
    }

    override fun show(
        notificationId: Int,
        channelId: String,
        title: String,
        message: String,
        @DrawableRes smallIconRes: Int,
        autoCancel: Boolean
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            reportLogger.d("POST_NOTIFICATIONS not granted, skipping notification $notificationId")
            return
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(smallIconRes)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(autoCancel)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }
}
