package com.budgeto.feature.spendingmoney.data.service

import com.budgeto.core.database.dao.SpendingDao
import com.budgeto.core.error.DataError
import com.budgeto.core.error.Resource
import com.budgeto.core.error.mapper.toLocalError
import com.budgeto.core.logger.ReportLogger
import com.budgeto.feature.spendingmoney.data.mapper.toEntity
import com.budgeto.feature.spendingmoney.data.mapper.toListModel
import com.budgeto.feature.spendingmoney.data.model.SpendingModel
import javax.inject.Inject

class SpendingMoneyLocalImpl @Inject constructor(
    private val spendingDao: SpendingDao,
    private val reportLogger: ReportLogger
) : SpendingMoneyLocal {

    override suspend fun insertSpending(spending: SpendingModel): Resource<Unit, DataError> {
        return try {
            Resource.Success(spendingDao.insertSpending(spending.toEntity()))
        } catch (e: Exception) {
            reportLogger.e("insertSpending failed", e)
            Resource.Failure(e.toLocalError())
        }
    }

    override suspend fun getTotalSpendingByMonth(month: String): Resource<Long, DataError> {
        return try {
            Resource.Success(spendingDao.getTotalSpendingByMonth(month))
        } catch (e: Exception) {
            reportLogger.e("getTotalSpendingByMonth failed", e)
            Resource.Failure(e.toLocalError())
        }
    }

    override suspend fun getSpendingByMonth(month: String): Resource<List<SpendingModel>, DataError> {
        return try {
            Resource.Success(spendingDao.getSpendingByMonth(month).toListModel())
        } catch (e: Exception) {
            reportLogger.e("getSpendingByMonth failed", e)
            Resource.Failure(e.toLocalError())
        }
    }

    override suspend fun getSpendingByCategory(category: String): Resource<List<SpendingModel>, DataError> {
        return try {
            Resource.Success(spendingDao.getSpendingByCategory(category).toListModel())
        } catch (e: Exception) {
            reportLogger.e("getSpendingByCategory failed", e)
            Resource.Failure(e.toLocalError())
        }
    }

    override suspend fun getAllSpending(): Resource<List<SpendingModel>, DataError> {
        return try {
            Resource.Success(spendingDao.getAllSpending().toListModel())
        } catch (e: Exception) {
            reportLogger.e("getAllSpending failed", e)
            Resource.Failure(e.toLocalError())
        }
    }

    override suspend fun getSpendingBySpendingType(spendingType: String): Resource<List<SpendingModel>, DataError> {
        return try {
            Resource.Success(spendingDao.getSpendingBySpendingType(spendingType).toListModel())
        } catch (e: Exception) {
            reportLogger.e("getSpendingBySpendingType failed", e)
            Resource.Failure(e.toLocalError())
        }
    }
}