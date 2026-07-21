package com.budgeto.feature.spendingmoney.domain.usecase

import com.budgeto.core.error.DomainError
import com.budgeto.core.error.Resource
import com.budgeto.feature.spendingmoney.domain.entity.Spending
import com.budgeto.feature.spendingmoney.domain.repository.SpendingMoneyRepository

class GetSpendingByMonthUseCase(
    private val repository: SpendingMoneyRepository
) {
    suspend operator fun invoke(month: String): Resource<List<Spending>, DomainError> {
        return repository.getSpendingByMonth(month)
    }
}