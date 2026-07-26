package com.budgeto.feature.balance.domain.enums

enum class MonthlyBalanceAlert(val label: String, val color: Long) {
    SAFE("Safe", 0xFF4CAF50),
    WARNING("Warning", 0xFFFFC107),
    OVER_BUDGET("Over Budget", 0xFFF44336),
    UNKNOWN("Unknown", 0xFF000000);

    companion object {
        fun getAlertByName(value: String): MonthlyBalanceAlert {
            return entries.firstOrNull {
                it.label.equals(value, ignoreCase = true)
            } ?: UNKNOWN
        }
        fun getAlertByColor(value: Long): MonthlyBalanceAlert {
            return entries.firstOrNull {
                it.color == value
            } ?: UNKNOWN
        }

    }

}