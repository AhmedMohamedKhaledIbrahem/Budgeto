package com.budgeto.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly_budget_table")
data class MonthlyBudgetEntity(
    @PrimaryKey
    val month: String,
    val budgetCents: Long
)
