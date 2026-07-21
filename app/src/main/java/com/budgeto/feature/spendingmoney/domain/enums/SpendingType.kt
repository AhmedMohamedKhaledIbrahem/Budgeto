package com.budgeto.feature.spendingmoney.domain.enums

enum class SpendingType(val type: String) {
    WALLET("Wallet"),
    DEBIT_CARD("Debit Card"),
    CREDIT_CARD("Credit Card"),
    INSTA_PAY("Insta Pay");

    companion object {
        fun getSpendingType(type: String): SpendingType {
            return entries.firstOrNull {
                it.type.equals(type, ignoreCase = true)
            } ?: WALLET
        }
    }
}