package com.budgeto.feature.spendingmoney.presentation.screen.component

enum class SpendingIconOption(val id: Int, val label: String) {
    WALLET(0, "Wallet"),
    DEBIT_CARD(1, "Debit Card"),
    CREDIT_CARD(2, "Credit Card"),
    INSTA_PAY(3, "Insta Pay");

    companion object {
        fun fromId(id: Int): SpendingIconOption? = entries.firstOrNull { it.id == id }
    }
}