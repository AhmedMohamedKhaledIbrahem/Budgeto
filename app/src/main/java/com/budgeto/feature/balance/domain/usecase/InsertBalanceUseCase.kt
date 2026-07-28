package com.budgeto.feature.balance.domain.usecase

import com.budgeto.feature.balance.domain.entity.MonthlyBudget
import com.budgeto.feature.balance.domain.repository.BalanceRepository
import javax.inject.Inject

class InsertBalanceUseCase @Inject constructor(
    private val balanceRepository: BalanceRepository
) {
    suspend operator fun invoke(monthlyBudget: MonthlyBudget) =
        balanceRepository.insertBalance(monthlyBudget)

}