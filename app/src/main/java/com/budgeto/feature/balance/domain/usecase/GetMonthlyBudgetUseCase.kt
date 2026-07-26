package com.budgeto.feature.balance.domain.usecase

import com.budgeto.feature.balance.domain.repository.BalanceRepository
import javax.inject.Inject

class GetMonthlyBudgetUseCase @Inject constructor(
    private val balanceRepository: BalanceRepository
) {
    suspend operator fun invoke(startDate: Long, endDate: Long) =
        balanceRepository.getMonthlyBudget(startDate, endDate)
}