package com.budgeto.feature.spendingmoney.presentation.intent

import com.budgeto.core.ui.view.ViewIntent
import com.budgeto.feature.spendingmoney.domain.enums.CategoryType
import com.budgeto.feature.spendingmoney.domain.enums.SpendingType

sealed interface SpendingIntent : ViewIntent {
    data object AddNewSpending : SpendingIntent
    data object AddNewSpendingClicked : SpendingIntent
    data object LoadSpending : SpendingIntent
    data object FilterAllSpending : SpendingIntent
    data class FilterByMonth(val month: String) : SpendingIntent
    data class FilterByCategory(val category: CategoryType) : SpendingIntent
    data class FilterBySpendingType(val spendingType: SpendingType) : SpendingIntent
    data class TotalAmountByMonth(val month: String) : SpendingIntent

}