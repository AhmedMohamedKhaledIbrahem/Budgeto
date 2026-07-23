package com.budgeto.feature.spendingmoney.domain.usecase

import com.budgeto.core.error.DomainError
import com.budgeto.core.error.Resource
import com.budgeto.feature.spendingmoney.domain.repository.SpendingMoneyRepository
import javax.inject.Inject

class GetTotalSpendingByMonthUseCase @Inject constructor(
    private val repository: SpendingMoneyRepository
) {
    suspend operator fun invoke(month: String): Resource<Long, DomainError> {
        return repository.getTotalSpendingByMonth(month)
    }
}