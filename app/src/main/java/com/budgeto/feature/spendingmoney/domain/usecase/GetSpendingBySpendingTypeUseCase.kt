package com.budgeto.feature.spendingmoney.domain.usecase

import com.budgeto.core.error.DomainError
import com.budgeto.core.error.Resource
import com.budgeto.feature.spendingmoney.domain.entity.Spending
import com.budgeto.feature.spendingmoney.domain.repository.SpendingMoneyRepository

class GetSpendingBySpendingTypeUseCase(
    private val repository: SpendingMoneyRepository
) {
    suspend operator fun invoke(spendingType: String): Resource<List<Spending>, DomainError> {
        return repository.getSpendingBySpendingType(spendingType)
    }
}