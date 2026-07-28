package com.budgeto.feature.balance.domain.usecase

import com.budgeto.core.error.Resource
import com.budgeto.core.utils.convertAmountToCents
import com.budgeto.core.utils.toMonthRange
import com.budgeto.feature.balance.domain.enums.MonthlyBalanceAlert
import com.budgeto.feature.balance.domain.notifier.BalanceAlertNotifier
import com.budgeto.feature.balance.domain.repository.BalanceAlertStateRepository
import com.budgeto.feature.spendingmoney.domain.usecase.GetTotalSpendingByMonthUseCase
import javax.inject.Inject

/**
 * Detects whether the monthly balance alert just transitioned into WARNING/OVER_BUDGET
 * and, if so, triggers exactly one notification for that transition.
 */
class CheckAndNotifyBalanceAlertUseCase @Inject constructor(
    private val getMonthlyBudgetUseCase: GetMonthlyBudgetUseCase,
    private val getTotalSpendingByMonthUseCase: GetTotalSpendingByMonthUseCase,
    private val calculateMonthlyBalanceAlertUseCase: CalculateMonthlyBalanceAlertUseCase,
    private val balanceAlertStateRepository: BalanceAlertStateRepository,
    private val balanceAlertNotifier: BalanceAlertNotifier
) {
    suspend operator fun invoke(referenceDateMillis: Long) {
        val (start, end) = referenceDateMillis.toMonthRange()

        val budget = (getMonthlyBudgetUseCase(start, end) as? Resource.Success)?.data ?: return
        val spent = (getTotalSpendingByMonthUseCase(start, end) as? Resource.Success)?.data ?: 0L
        val currentAlert = (calculateMonthlyBalanceAlertUseCase(
            spent = spent,
            monthlyBudget = budget.amount.convertAmountToCents()
        ) as? Resource.Success)?.data ?: return

        val lastAlert = (balanceAlertStateRepository.getLastAlert(start) as? Resource.Success)?.data
        balanceAlertStateRepository.saveLastAlert(start, currentAlert)

        val isAlertWorthy = currentAlert == MonthlyBalanceAlert.WARNING ||
            currentAlert == MonthlyBalanceAlert.OVER_BUDGET

        if (currentAlert != lastAlert && isAlertWorthy) {
            balanceAlertNotifier.notifyAlertChanged(currentAlert)
        }
    }
}
