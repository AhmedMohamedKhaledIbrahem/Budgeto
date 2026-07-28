package com.budgeto.feature.balance.domain.notifier

import com.budgeto.feature.balance.domain.enums.MonthlyBalanceAlert

interface BalanceAlertNotifier {
    fun notifyAlertChanged(alert: MonthlyBalanceAlert)
}
