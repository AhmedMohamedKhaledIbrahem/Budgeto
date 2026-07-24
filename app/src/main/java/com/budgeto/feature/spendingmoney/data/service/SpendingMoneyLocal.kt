package com.budgeto.feature.spendingmoney.data.service

import com.budgeto.core.error.DataError
import com.budgeto.core.error.Resource
import com.budgeto.feature.spendingmoney.data.model.SpendingModel

interface SpendingMoneyLocal {
    suspend fun insertSpending(spending: SpendingModel): Resource<Unit, DataError>
    suspend fun getTotalSpendingByMonth(startDate: Long, endDate: Long): Resource<Long, DataError>
    suspend fun getSpendingByMonth(month: String): Resource<List<SpendingModel>, DataError>
    suspend fun getSpendingByCategory(category: String): Resource<List<SpendingModel>, DataError>
    suspend fun getAllSpending(): Resource<List<SpendingModel>, DataError>
    suspend fun getSpendingBySpendingType(spendingType: String): Resource<List<SpendingModel>, DataError>
}