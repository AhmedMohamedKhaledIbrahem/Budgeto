package com.budgeto.feature.spendingmoney.domain.enums

import com.budgeto.R

enum class CategoryType(val type: String,val icon: Int) {
    GROCERY("Grocery", R.drawable.ic_groceries),
    BILL("Bill",R.drawable.ic_invoice_bill),
    TRANSPORT("Transport",R.drawable.ic_transport),
    MEDICINE("Medicine",R.drawable.ic_medicine),
    OTHER("Other",R.drawable.ic_other);

    companion object {
        fun getCategoryType(category: String): CategoryType {
            return entries.firstOrNull {
                it.type.equals(category, ignoreCase = true)
            } ?: OTHER
        }
    }
}