package com.budgeto.feature.balance.data.worker

import android.app.NotificationManager
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.budgeto.R
import com.budgeto.core.notification.NotificationHelper
import com.budgeto.feature.balance.domain.enums.MonthlyBalanceAlert
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class BalanceAlertNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val alert = inputData.getString(KEY_ALERT)
            ?.let { runCatching { MonthlyBalanceAlert.valueOf(it) }.getOrNull() }
            ?: return Result.failure()

        val (title, message) = when (alert) {
            MonthlyBalanceAlert.WARNING ->
                "Budget Warning" to "You've used 70% or more of your monthly budget."
            MonthlyBalanceAlert.OVER_BUDGET ->
                "Over Budget" to "You've exceeded your monthly budget."
            else -> return Result.success()
        }

        notificationHelper.ensureChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationHelper.show(
            notificationId = NOTIFICATION_ID,
            channelId = CHANNEL_ID,
            title = title,
            message = message,
            smallIconRes = R.drawable.ic_wallet
        )

        return Result.success()
    }

    companion object {
        const val KEY_ALERT = "key_alert"
        const val UNIQUE_WORK_NAME = "balance_alert_notification_work"
        const val CHANNEL_ID = "balance_alert_channel"
        const val CHANNEL_NAME = "Balance Alerts"
        const val NOTIFICATION_ID = 2001
    }
}
