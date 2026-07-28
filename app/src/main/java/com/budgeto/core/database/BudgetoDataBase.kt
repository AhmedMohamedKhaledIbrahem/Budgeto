package com.budgeto.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.budgeto.core.database.dao.MonthlyAlertStateDao
import com.budgeto.core.database.dao.MonthlyBudgetDao
import com.budgeto.core.database.dao.SpendingDao
import com.budgeto.core.database.entity.MonthlyAlertStateEntity
import com.budgeto.core.database.entity.MonthlyBudgetEntity
import com.budgeto.core.database.entity.SpendingEntity

@Database(
    entities = [
        MonthlyBudgetEntity::class,
        SpendingEntity::class,
        MonthlyAlertStateEntity::class
    ],
    version = 3,
    exportSchema = false,
)
abstract class BudgetoDataBase: RoomDatabase() {
    abstract fun monthlyBudgetDao(): MonthlyBudgetDao
    abstract fun spendingDao(): SpendingDao
    abstract fun monthlyAlertStateDao(): MonthlyAlertStateDao
}