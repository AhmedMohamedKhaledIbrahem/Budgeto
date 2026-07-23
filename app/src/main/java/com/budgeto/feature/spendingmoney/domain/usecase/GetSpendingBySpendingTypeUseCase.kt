package com.budgeto.feature.spendingmoney.domain.usecase

import com.budgeto.core.error.DomainError
import com.budgeto.core.error.Resource
import com.budgeto.core.utils.fold
import com.budgeto.core.utils.toHeaderDateLabel
import com.budgeto.feature.spendingmoney.domain.entity.GroupedSpending
import com.budgeto.feature.spendingmoney.domain.entity.Spending
import com.budgeto.feature.spendingmoney.domain.repository.SpendingMoneyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.collections.component1
import kotlin.collections.component2
import javax.inject.Inject

class GetSpendingBySpendingTypeUseCase @Inject constructor(
    private val repository: SpendingMoneyRepository
) {
    suspend operator fun invoke(spendingType: String): Resource<List<GroupedSpending>, DomainError> {
        return withContext(Dispatchers.IO) {
            repository.getSpendingBySpendingType(spendingType).fold(
                onSuccess = { spendingList ->
                    val groupedSpending = spendingList
                        .sortedByDescending { it.date }
                        .groupBy { it.date.toHeaderDateLabel() }
                        .map { (header, items) ->
                            GroupedSpending(header, items)
                        }
                    Resource.Success(groupedSpending)
                },
                onFailure = {
                    Resource.Failure(it)
                }
            )
        }
    }
}