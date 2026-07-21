package com.budgeto.feature.spendingmoney.domain.usecase

import com.budgeto.core.error.DomainError
import com.budgeto.core.error.Resource
import com.budgeto.feature.spendingmoney.domain.entity.Spending
import com.budgeto.feature.spendingmoney.domain.repository.SpendingMoneyRepository

class GetAllSpendingUseCase(
    private val repository: SpendingMoneyRepository
) {
    suspend operator fun invoke(): Resource<List<Spending>, DomainError> {
        return repository.getAllSpending()
    }
}