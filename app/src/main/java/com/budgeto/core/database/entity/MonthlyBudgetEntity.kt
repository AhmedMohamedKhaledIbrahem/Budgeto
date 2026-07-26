package com.budgeto.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly_budget_table")
data class MonthlyBudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val month: Long,
    val budgetCents: Long
)
