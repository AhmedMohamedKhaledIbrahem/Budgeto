package com.budgeto.feature.balance.domain.repository

import com.budgeto.core.error.DomainError
import com.budgeto.core.error.Resource
import com.budgeto.feature.balance.domain.entity.MonthlyBudget
import com.budgeto.feature.balance.domain.enums.MonthlyBalanceAlert

interface BalanceRepository {
    suspend fun insertBalance(monthlyBudget: MonthlyBudget): Resource<Unit, DomainError>
    suspend fun getMonthlyBudget(startDate: Long, endDate: Long): Resource<Long, DomainError>
    suspend fun calculateMonthlyBalance(
        spent: Long,
        monthlyBudget: Long
    ): Resource<MonthlyBalanceAlert, DomainError>
}