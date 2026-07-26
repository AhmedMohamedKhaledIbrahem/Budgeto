package com.budgeto.feature.balance.data.repository

import com.budgeto.core.error.DomainError
import com.budgeto.core.error.Resource
import com.budgeto.core.error.mapper.toDomainError
import com.budgeto.core.error.mapper.toLocalError
import com.budgeto.core.utils.fold
import com.budgeto.feature.balance.data.mapper.toDomain
import com.budgeto.feature.balance.data.mapper.toModel
import com.budgeto.feature.balance.data.service.BalanceLocal
import com.budgeto.feature.balance.domain.entity.MonthlyBudget
import com.budgeto.feature.balance.domain.enums.MonthlyBalanceAlert
import com.budgeto.feature.balance.domain.repository.BalanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BalanceRepositoryImpl @Inject constructor(
    private val balanceLocal: BalanceLocal
) : BalanceRepository {
    override suspend fun insertBalance(monthlyBudget: MonthlyBudget): Resource<Unit, DomainError> {
        return balanceLocal.insertBalance(monthlyBudget.toModel()).fold(
            onFailure = {
                Resource.Failure(it.toDomainError())
            },
            onSuccess = {
                Resource.Success(Unit)
            }
        )
    }

    override suspend fun getMonthlyBudget(
        startDate: Long,
        endDate: Long
    ): Resource<MonthlyBudget?, DomainError> {
        return balanceLocal.getMonthlyBudget(startDate, endDate).fold(
            onFailure = {
                Resource.Failure(it.toDomainError())
            },
            onSuccess = {
                Resource.Success(it?.toDomain())
            }
        )
    }

    override suspend fun calculateMonthlyBalance(
        spent: Long,
        monthlyBudget: Long
    ): Resource<MonthlyBalanceAlert, DomainError> = withContext(Dispatchers.IO) {
        try {
            if (monthlyBudget <= 0) return@withContext Resource.Success(MonthlyBalanceAlert.OVER_BUDGET)
            val percentage = spent / monthlyBudget
            return@withContext when {
                percentage >= 0.9 -> Resource.Success(MonthlyBalanceAlert.OVER_BUDGET)
                percentage >= 0.7 -> Resource.Success(MonthlyBalanceAlert.WARNING)
                else -> Resource.Success(MonthlyBalanceAlert.SAFE)
            }
        } catch (e: Exception) {
            Resource.Failure(e.toLocalError().toDomainError())
        }
    }
}