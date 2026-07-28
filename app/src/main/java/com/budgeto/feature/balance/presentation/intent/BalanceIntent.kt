package com.budgeto.feature.balance.presentation.intent

import com.budgeto.core.ui.view.ViewIntent
import com.budgeto.feature.balance.domain.entity.MonthlyBudget

sealed interface BalanceIntent : ViewIntent {
    data class InsertBalance(val monthlyBudget: MonthlyBudget): BalanceIntent
    data class CalculateMonthlyBalance(val startDay: Long,val endDay: Long) : BalanceIntent
    data object AddBalanceClicked : BalanceIntent
    data object DismissBalanceDialog : BalanceIntent
    data class EnterMonth(val startDay: Long,val endDay: Long): BalanceIntent
}