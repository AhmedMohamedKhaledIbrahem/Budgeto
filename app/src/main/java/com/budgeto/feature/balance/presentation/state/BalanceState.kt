package com.budgeto.feature.balance.presentation.state

import androidx.compose.runtime.Stable
import com.budgeto.core.ui.view.ViewState
import com.budgeto.feature.balance.domain.entity.MonthlyBudget
import com.budgeto.feature.balance.domain.enums.MonthlyBalanceAlert

@Stable
data class BalanceState(
    val isLoading: Boolean = false,
    val isDialogVisible: Boolean = false,
    val spent: Long = 0,
    val monthlyBudget: MonthlyBudget? = null,
    val startDate: Long = 0,
    val endDate: Long = 0,
    val alert: MonthlyBalanceAlert = MonthlyBalanceAlert.UNKNOWN
) : ViewState
