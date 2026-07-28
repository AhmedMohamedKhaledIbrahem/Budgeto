package com.budgeto.feature.balance.data.service

import com.budgeto.core.error.DataError
import com.budgeto.core.error.Resource
import com.budgeto.feature.balance.data.model.MonthlyBudgetModel

interface BalanceLocal {
    suspend fun insertBalance(monthlyBudgetModel: MonthlyBudgetModel): Resource<Unit, DataError>
    suspend fun getMonthlyBudget(startDate: Long, endDate: Long): Resource<MonthlyBudgetModel?, DataError>
}