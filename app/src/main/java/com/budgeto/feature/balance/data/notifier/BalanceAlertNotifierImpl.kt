package com.budgeto.feature.balance.data.notifier

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.budgeto.feature.balance.data.worker.BalanceAlertNotificationWorker
import com.budgeto.feature.balance.domain.enums.MonthlyBalanceAlert
import com.budgeto.feature.balance.domain.notifier.BalanceAlertNotifier
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BalanceAlertNotifierImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : BalanceAlertNotifier {

    override fun notifyAlertChanged(alert: MonthlyBalanceAlert) {
        val inputData = workDataOf(BalanceAlertNotificationWorker.KEY_ALERT to alert.name)
        val request = OneTimeWorkRequestBuilder<BalanceAlertNotificationWorker>()
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            BalanceAlertNotificationWorker.UNIQUE_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}
