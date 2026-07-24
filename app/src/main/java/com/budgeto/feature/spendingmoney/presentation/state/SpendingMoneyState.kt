package com.budgeto.feature.spendingmoney.presentation.state

import androidx.compose.runtime.Stable
import com.budgeto.core.ui.view.ViewState
import com.budgeto.feature.spendingmoney.domain.entity.GroupedSpending
import com.budgeto.feature.spendingmoney.domain.entity.Spending


@Stable
data class SpendingMoneyState(
    val isLoading: Boolean = false,
    val isSpendingLoadedFromDb: Boolean = false,
    val groupedSpending: List<GroupedSpending> = emptyList(),
    val spending: Spending? = null,
    val totalAmount: String? = null,
) : ViewState