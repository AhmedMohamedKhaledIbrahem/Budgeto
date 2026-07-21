package com.budgeto.feature.spendingmoney.domain.enums

enum class CategoryType(val type: String) {
    GROCERY("Grocery"),
    BILL("Bill"),
    TRANSPORT("Transport"),
    MEDICINE("Medicine"),
    OTHER("Other");

    companion object {
        fun getCategoryType(category: String): CategoryType {
            return entries.firstOrNull {
                it.type.equals(category, ignoreCase = true)
            } ?: OTHER
        }
    }
}