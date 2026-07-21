package com.budgeto.feature.spendingmoney.data.model

data class SpendingModel(
    val id: Long,
    val description: String,
    val amountCents: Long,
    val date: Long,
    val category: String,
    val spendingType: String,
    val icon: Int
)
