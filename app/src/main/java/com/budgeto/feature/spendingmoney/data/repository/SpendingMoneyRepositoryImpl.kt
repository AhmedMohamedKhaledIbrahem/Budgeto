package com.budgeto.feature.spendingmoney.data.repository

import com.budgeto.core.error.DomainError
import com.budgeto.core.error.Resource
import com.budgeto.core.error.Resource.Failure
import com.budgeto.core.error.Resource.Success
import com.budgeto.core.error.mapper.toDomainError
import com.budgeto.core.utils.fold
import com.budgeto.feature.spendingmoney.data.mapper.toListSpending
import com.budgeto.feature.spendingmoney.data.mapper.toModel
import com.budgeto.feature.spendingmoney.data.service.SpendingMoneyLocal
import com.budgeto.feature.spendingmoney.domain.entity.Spending
import com.budgeto.feature.spendingmoney.domain.repository.SpendingMoneyRepository
import javax.inject.Inject

class SpendingMoneyRepositoryImpl @Inject constructor(
    private val spendingMoneyLocal: SpendingMoneyLocal
) : SpendingMoneyRepository {

    override suspend fun insertSpending(spending: Spending): Resource<Unit, DomainError> {
        return spendingMoneyLocal.insertSpending(spending.toModel()).fold(
            onFailure = {
                Failure(it.toDomainError())
            },
            onSuccess = {
                Success(it)
            }
        )
    }

    override suspend fun getTotalSpendingByMonth(startDate: Long, endDate: Long): Resource<Long, DomainError> {
        return spendingMoneyLocal.getTotalSpendingByMonth(startDate, endDate).fold(
            onFailure = {
                Failure(it.toDomainError())
            },
            onSuccess = {
                Success(it)
            }
        )
    }

    override suspend fun getSpendingByMonth(month: String): Resource<List<Spending>, DomainError> {
        return spendingMoneyLocal.getSpendingByMonth(month).fold(
            onFailure = {
                Failure(it.toDomainError())
            },
            onSuccess = {
                Success(it.toListSpending())
            }
        )
    }

    override suspend fun getSpendingByCategory(category: String): Resource<List<Spending>, DomainError> {
        return spendingMoneyLocal.getSpendingByCategory(category).fold(
            onFailure = {
                Failure(it.toDomainError())
            },
            onSuccess = {
                Success(it.toListSpending())
            }
        )
    }

    override suspend fun getAllSpending(): Resource<List<Spending>, DomainError> {
        return spendingMoneyLocal.getAllSpending().fold(
            onFailure = {
                Failure(it.toDomainError())
            },
            onSuccess = {
                Success(it.toListSpending())
            }
        )
    }

    override suspend fun getSpendingBySpendingType(spendingType: String): Resource<List<Spending>, DomainError> {
        return spendingMoneyLocal.getSpendingBySpendingType(spendingType).fold(
            onFailure = {
                Failure(it.toDomainError())
            },
            onSuccess = {
                Success(it.toListSpending())
            }
        )
    }

}