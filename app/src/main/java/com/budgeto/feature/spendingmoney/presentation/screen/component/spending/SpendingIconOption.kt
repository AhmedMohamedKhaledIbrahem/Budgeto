package com.budgeto.feature.spendingmoney.presentation.screen.component.spending

import com.budgeto.R

enum class SpendingIconOption(val id: Int, val label: String) {
    WALLET(R.drawable.ic_wallet, "Wallet"),
    DEBIT_CARD(R.drawable.ic_debit_card, "Debit Card"),
    CREDIT_CARD(R.drawable.ic_credit_card, "Credit Card"),
    INSTA_PAY(R.drawable.ic_insta_pay, "Insta Pay");

    companion object {
        fun fromId(id: Int): SpendingIconOption? = entries.firstOrNull { it.id == id }
    }
}