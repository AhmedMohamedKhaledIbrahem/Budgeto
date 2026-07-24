package com.budgeto.feature.spendingmoney.presentation.viewmodel

import com.budgeto.core.error.mapper.asUiTextOrDefault
import com.budgeto.core.event.UiEvent
import com.budgeto.core.navigation.Route
import com.budgeto.core.ui.UiText
import com.budgeto.core.ui.base.MviViewModel
import com.budgeto.core.utils.convertCentsToAmount
import com.budgeto.core.utils.onUseCase
import com.budgeto.feature.spendingmoney.domain.entity.Spending
import com.budgeto.feature.spendingmoney.domain.enums.CategoryType
import com.budgeto.feature.spendingmoney.domain.enums.SpendingType
import com.budgeto.feature.spendingmoney.domain.usecase.GetAllSpendingUseCase
import com.budgeto.feature.spendingmoney.domain.usecase.GetSpendingByCategoryUseCase
import com.budgeto.feature.spendingmoney.domain.usecase.GetSpendingByMonthUseCase
import com.budgeto.feature.spendingmoney.domain.usecase.GetSpendingBySpendingTypeUseCase
import com.budgeto.feature.spendingmoney.domain.usecase.GetTotalSpendingByMonthUseCase
import com.budgeto.feature.spendingmoney.domain.usecase.InsertSpendingUseCase
import com.budgeto.feature.spendingmoney.presentation.intent.SpendingIntent
import com.budgeto.feature.spendingmoney.presentation.state.SpendingMoneyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SpendingViewModel @Inject constructor(
    private val getAllSpendingUseCase: GetAllSpendingUseCase,
    private val addSpendingUseCase: InsertSpendingUseCase,
    private val getSpendingByMonthUseCase: GetSpendingByMonthUseCase,
    private val getSpendingByCategoryUseCase: GetSpendingByCategoryUseCase,
    private val getSpendingBySpendingTypeUseCase: GetSpendingBySpendingTypeUseCase,
    private val getTotalSpendingByMonthUseCase: GetTotalSpendingByMonthUseCase
) : MviViewModel<SpendingMoneyState, SpendingIntent, UiEvent>() {
    override val initialState: SpendingMoneyState
        get() = SpendingMoneyState()

    override fun onIntent(intent: SpendingIntent) {
        when (intent) {
            is SpendingIntent.AddNewSpending -> {
                addNewSpending(intent.spending)
            }

            is SpendingIntent.FilterAllSpending -> {
                getFilterAll()
            }

            is SpendingIntent.AddNewSpendingClicked -> {
                sendEvent(UiEvent.Navigate(Route.SpendingScreen))
            }

            is SpendingIntent.TotalAmountByMonth -> {
                getTotalAmount(intent.startDate, intent.endDate)
            }

            is SpendingIntent.FilterByMonth -> {
                getFilterByMonth(intent.month)
            }

            is SpendingIntent.FilterByCategory -> {
                getFilterByCategory(intent.category)
            }

            is SpendingIntent.FilterBySpendingType -> {
                getFilterBySpending(intent.spendingType)
            }

            is SpendingIntent.LoadSpending -> {
                if (!state.value.isSpendingLoadedFromDb) getAllSpending()
            }

            is SpendingIntent.SpendingMoneyDetailsClicked -> {
                sendEvent(
                    UiEvent.Navigate(
                        Route.SpendingDetailsScreen(intent.spending)
                    )
                )
            }
            is SpendingIntent.BackToSpendingScreen -> {
                sendEvent(
                    UiEvent.BackToPreviousScreen
                )
            }
        }
    }

    private fun addNewSpending(spending: Spending) {
        if (
            spending.amount.isBlank() &&
            spending.description.isBlank() &&
            spending.category.isBlank() &&
            spending.spendingType.isBlank() &&
            spending.icon == -1
        ) {
            sendEvent(UiEvent.ShowSnackBar(UiText.from("all fields are required")))
            return
        } else {
            onUseCase(
                useCase = {
                    _state.update { it.copy(isLoading = true) }
                    addSpendingUseCase.invoke(spending)
                },
                onSuccess = {
                    _state.update { it.copy(isLoading = false, isSpendingLoadedFromDb = false) }
                    sendEvent(
                        UiEvent.CombineEvents(
                            listOf(
                                UiEvent.ShowSnackBar(UiText.from("spending added successfully")),
                                UiEvent.Navigate(Route.HomeScreen)
                            )
                        )
                    )
                },
                onFailure = { error ->
                    _state.update { it.copy(isLoading = false) }
                    sendEvent(UiEvent.ShowSnackBar(error.asUiTextOrDefault()))
                }
            )
        }
    }

    private fun getAllSpending() {
        onUseCase(
            useCase = {
                _state.update { it.copy(isLoading = true) }
                getAllSpendingUseCase.invoke()
            },
            onSuccess = { groupedSpending ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isSpendingLoadedFromDb = true,
                        groupedSpending = groupedSpending.distinct()
                    )
                }
            },
            onFailure = { error ->
                _state.update { it.copy(isLoading = false) }
                sendEvent(UiEvent.ShowSnackBar(error.asUiTextOrDefault()))

            }
        )
    }

    private fun getFilterAll() {
        onUseCase(
            useCase = {
                _state.update { it.copy(isLoading = true) }
                getAllSpendingUseCase.invoke()
            },
            onSuccess = { groupedSpending ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isSpendingLoadedFromDb = true,
                        groupedSpending = groupedSpending.distinct()
                    )
                }
            },
            onFailure = { error ->
                _state.update { it.copy(isLoading = false) }
                sendEvent(UiEvent.ShowSnackBar(error.asUiTextOrDefault()))

            }
        )
    }

    private fun getFilterByCategory(category: CategoryType) {
        onUseCase(
            useCase = {
                _state.update { it.copy(isLoading = true) }
                getSpendingByCategoryUseCase.invoke(category.type)
            },
            onSuccess = { groupedSpending ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isSpendingLoadedFromDb = true,
                        groupedSpending = groupedSpending.distinct()
                    )
                }
            },
            onFailure = { error ->
                _state.update { it.copy(isLoading = false) }
                sendEvent(UiEvent.ShowSnackBar(error.asUiTextOrDefault()))

            }
        )
    }

    private fun getFilterBySpending(spendingType: SpendingType) {
        onUseCase(
            useCase = {
                _state.update { it.copy(isLoading = true) }
                getSpendingBySpendingTypeUseCase.invoke(spendingType.type)
            },
            onSuccess = { groupedSpending ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isSpendingLoadedFromDb = true,
                        groupedSpending = groupedSpending.distinct()
                    )
                }
            },
            onFailure = { error ->
                _state.update { it.copy(isLoading = false) }
                sendEvent(UiEvent.ShowSnackBar(error.asUiTextOrDefault()))

            }
        )
    }

    private fun getFilterByMonth(month: String) {
        onUseCase(
            useCase = {
                _state.update { it.copy(isLoading = true) }
                getSpendingByMonthUseCase.invoke(month)
            },
            onSuccess = { groupedSpending ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isSpendingLoadedFromDb = true,
                        groupedSpending = groupedSpending.distinct()
                    )
                }
            },
            onFailure = { error ->
                _state.update { it.copy(isLoading = false) }
                sendEvent(UiEvent.ShowSnackBar(error.asUiTextOrDefault()))

            }
        )
    }

    private fun getTotalAmount(startDate: Long, endDate: Long) {
        onUseCase(
            useCase = {
                _state.update { it.copy(isLoading = true) }
                getTotalSpendingByMonthUseCase.invoke(startDate, endDate)
            },
            onSuccess = { amount ->
                if (amount == 0L) {
                    _state.update {
                        it.copy(isLoading = false, totalAmount = (0L).convertCentsToAmount())
                    }
                } else {
                    _state.update {
                        it.copy(isLoading = false, totalAmount = amount.convertCentsToAmount())
                    }
                }

            },
            onFailure = { error ->
                _state.update { it.copy(isLoading = false) }
                sendEvent(UiEvent.ShowSnackBar(error.asUiTextOrDefault()))
            }
        )
    }

}