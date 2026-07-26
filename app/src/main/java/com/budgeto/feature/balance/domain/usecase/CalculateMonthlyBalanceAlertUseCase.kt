package com.budgeto.feature.balance.domain.usecase

import com.budgeto.core.error.DomainError
import com.budgeto.core.error.Resource
import com.budgeto.feature.balance.domain.enums.MonthlyBalanceAlert
import com.budgeto.feature.balance.domain.repository.BalanceRepository
import javax.inject.Inject

class CalculateMonthlyBalanceAlertUseCase @Inject constructor(
    private val balanceRepository: BalanceRepository
) {
    suspend operator fun invoke(
        spent: Long,
        monthlyBudget: Long
    ): Resource<MonthlyBalanceAlert, DomainError> {
        return balanceRepository.calculateMonthlyBalance(spent, monthlyBudget)
    }
}