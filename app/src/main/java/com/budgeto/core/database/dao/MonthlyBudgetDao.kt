package com.budgeto.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.budgeto.core.database.entity.MonthlyBudgetEntity

@Dao
interface MonthlyBudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonthlyBudget(monthlyBudget: MonthlyBudgetEntity)

    @Query("SELECT budgetCents FROM monthly_budget_table WHERE month = :month ")
    suspend fun getMonthlyBudget(month: String): Long
}