package com.budgeto.feature.spendingmoney.domain.entity

data class GroupedSpending(
    val header: String,
    val items: List<Spending>
)
