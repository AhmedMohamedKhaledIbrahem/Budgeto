package com.budgeto.feature.balance.presentation.viewmodel

import com.budgeto.core.error.Resource
import com.budgeto.core.error.mapper.asUiTextOrDefault
import com.budgeto.core.event.UiEvent
import com.budgeto.core.ui.UiText
import com.budgeto.core.ui.base.MviViewModel
import com.budgeto.core.utils.convertAmountToCents
import com.budgeto.core.utils.onUseCase
import com.budgeto.feature.balance.domain.entity.MonthlyBudget
import com.budgeto.feature.balance.domain.usecase.CalculateMonthlyBalanceAlertUseCase
import com.budgeto.feature.balance.domain.usecase.GetMonthlyBudgetUseCase
import com.budgeto.feature.balance.domain.usecase.InsertBalanceUseCase
import com.budgeto.feature.balance.presentation.intent.BalanceIntent
import com.budgeto.feature.balance.presentation.state.BalanceState
import com.budgeto.feature.spendingmoney.domain.usecase.GetTotalSpendingByMonthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val insertBalanceUseCase: InsertBalanceUseCase,
    private val getMonthlyBudgetUseCase: GetMonthlyBudgetUseCase,
    private val calculateMonthlyBalanceUseCase: CalculateMonthlyBalanceAlertUseCase,
    private val getTotalSpendingByMonthUseCase: GetTotalSpendingByMonthUseCase
) : MviViewModel<BalanceState, BalanceIntent, UiEvent>() {
    override val initialState: BalanceState
        get() = BalanceState()

    override fun onIntent(intent: BalanceIntent) {
        when (intent) {
            is BalanceIntent.InsertBalance -> {
                insertBalance(intent.monthlyBudget)
            }

            is BalanceIntent.AddBalanceClicked -> {
                updateState { it.copy(isDialogVisible = true) }
            }

            is BalanceIntent.DismissBalanceDialog -> {
                updateState { it.copy(isDialogVisible = false) }
            }

            is BalanceIntent.CalculateMonthlyBalance -> {
                loadMonthlyBalance(intent.startDay, intent.endDay)
            }


            is BalanceIntent.EnterMonth -> {
                updateState {
                    it.copy(
                        startDate = intent.startDay,
                        endDate = intent.endDay
                    )
                }

            }

        }
    }

    private fun insertBalance(monthlyBudget: MonthlyBudget) {
        if (monthlyBudget.amount.isBlank() || monthlyBudget.month == 0L) {
            sendEvent(
                UiEvent.ShowSnackBar(
                    UiText.from("the amount and month are required")
                )
            )
            return
        }
        onUseCase(
            useCase = {
                updateState { it.copy(isLoading = true) }
                insertBalanceUseCase.invoke(
                    monthlyBudget = monthlyBudget
                )
            },
            onSuccess = {
                updateState { it.copy(isLoading = false, isDialogVisible = false) }
                sendEvent(
                    UiEvent.ShowSnackBar(
                        UiText.from("balance added successfully")
                    )
                )
            },
            onFailure = { error ->
                updateState { it.copy(isLoading = false) }
                sendEvent(
                    UiEvent.ShowSnackBar(
                        error.asUiTextOrDefault()
                    )
                )
            },
        )
    }

    private fun loadMonthlyBalance(startDate: Long, endDate: Long) {
        launch {
            updateState {
                it.copy(
                    isLoading = true,
                    startDate = startDate,
                    endDate = endDate
                )
            }

            combine(
                flow { emit(getMonthlyBudgetUseCase.invoke(startDate, endDate)) },
                flow { emit(getTotalSpendingByMonthUseCase.invoke(startDate, endDate)) }
            ) { budgetResource, spentResource -> budgetResource to spentResource }
                .collect { (budgetResource, spentResource) ->
                    if (budgetResource is Resource.Failure) {
                        sendEvent(UiEvent.ShowSnackBar(budgetResource.asUiTextOrDefault()))
                    }
                    if (spentResource is Resource.Failure) {
                        sendEvent(UiEvent.ShowSnackBar(spentResource.asUiTextOrDefault()))
                    }

                    val monthlyBudget = (budgetResource as? Resource.Success)?.data
                    val spent = (spentResource as? Resource.Success)?.data ?: 0L

                    updateState {
                        it.copy(
                            monthlyBudget = monthlyBudget,
                            spent = spent
                        )
                    }

                    when (
                        val alertResource = calculateMonthlyBalanceUseCase.invoke(
                            spent = spent,
                            monthlyBudget = monthlyBudget?.amount.convertAmountToCents()
                        )
                    ) {
                        is Resource.Success -> {
                            updateState {
                                it.copy(
                                    isLoading = false,
                                    alert = alertResource.data
                                )
                            }
                        }

                        is Resource.Failure -> {
                            updateState { it.copy(isLoading = false) }
                            sendEvent(UiEvent.ShowSnackBar(alertResource.asUiTextOrDefault()))
                        }
                    }
                }
        }
    }

}