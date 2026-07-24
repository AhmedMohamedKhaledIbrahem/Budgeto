package com.budgeto.feature.spendingmoney.domain.repository

import com.budgeto.core.error.DomainError
import com.budgeto.core.error.Resource
import com.budgeto.feature.spendingmoney.domain.entity.Spending

interface SpendingMoneyRepository {
    suspend fun insertSpending(spending: Spending): Resource<Unit, DomainError>
    suspend fun getTotalSpendingByMonth(startDate: Long, endDate: Long): Resource<Long, DomainError>
    suspend fun getSpendingByMonth(month: String): Resource<List<Spending>, DomainError>
    suspend fun getSpendingByCategory(category: String): Resource<List<Spending>, DomainError>
    suspend fun getAllSpending(): Resource<List<Spending>, DomainError>
    suspend fun getSpendingBySpendingType(spendingType: String): Resource<List<Spending>, DomainError>
}