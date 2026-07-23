package com.budgeto.feature.spendingmoney.domain.usecase

import com.budgeto.core.error.DomainError
import com.budgeto.core.error.Resource
import com.budgeto.feature.spendingmoney.domain.entity.Spending
import com.budgeto.feature.spendingmoney.domain.repository.SpendingMoneyRepository
import javax.inject.Inject

class InsertSpendingUseCase @Inject constructor(
    private val repository: SpendingMoneyRepository
) {
    suspend operator fun invoke(spending: Spending): Resource<Unit, DomainError> {
        return repository.insertSpending(spending)
    }
}