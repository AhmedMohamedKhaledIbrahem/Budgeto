package com.budgeto.feature.spendingmoney.domain.enums

import com.budgeto.R

enum class SpendingType(val type: String,val icon: Int) {
    WALLET("Wallet", R.drawable.ic_wallet),
    DEBIT_CARD("Debit Card" , R.drawable.ic_debit_card),
    CREDIT_CARD("Credit Card" , R.drawable.ic_credit_card),
    INSTA_PAY("Insta Pay" , R.drawable.ic_insta_pay);

    companion object {
        fun getSpendingType(type: String): SpendingType {
            return entries.firstOrNull {
                it.type.equals(type, ignoreCase = true)
            } ?: WALLET
        }
    }
}