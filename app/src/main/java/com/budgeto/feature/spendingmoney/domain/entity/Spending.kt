package com.budgeto.feature.spendingmoney.domain.entity

data class Spending(
    val id: Long,
    val description: String,
    val amount: String,
    val date: Long,
    val category: String,
    val spendingType: String,
    val icon: Int = -1
)
