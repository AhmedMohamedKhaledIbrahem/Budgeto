package com.budgeto.core.notification

import androidx.annotation.DrawableRes

interface NotificationHelper {
    fun ensureChannel(
        channelId: String,
        channelName: String,
        importance: Int,
        description: String = ""
    )

    fun show(
        notificationId: Int,
        channelId: String,
        title: String,
        message: String,
        @DrawableRes smallIconRes: Int,
        autoCancel: Boolean = true
    )
}
