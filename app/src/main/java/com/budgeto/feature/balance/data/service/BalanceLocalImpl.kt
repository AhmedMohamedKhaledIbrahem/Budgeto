package com.budgeto.feature.balance.data.service

import com.budgeto.core.database.dao.MonthlyBudgetDao
import com.budgeto.core.error.DataError
import com.budgeto.core.error.Resource
import com.budgeto.core.error.mapper.toLocalError
import com.budgeto.core.logger.ReportLogger
import com.budgeto.feature.balance.data.mapper.toEntity
import com.budgeto.feature.balance.data.mapper.toModel
import com.budgeto.feature.balance.data.model.MonthlyBudgetModel
import javax.inject.Inject

class BalanceLocalImpl @Inject constructor(
    private val balanceDao: MonthlyBudgetDao,
    reportLogger: ReportLogger
) : BalanceLocal, ReportLogger by reportLogger {
    override suspend fun insertBalance(monthlyBudgetModel: MonthlyBudgetModel): Resource<Unit, DataError> {
        return try {
            balanceDao.insertMonthlyBudget(monthlyBudgetModel.toEntity())
            Resource.Success(Unit)
        }catch (e: Exception){
            e("Error inserting monthly budget", e)
            Resource.Failure(e.toLocalError())
        }
    }

    override suspend fun getMonthlyBudget(
        startDate: Long,
        endDate: Long
    ): Resource<MonthlyBudgetModel?, DataError> {
        return try {
            Resource.Success(balanceDao.getMonthlyBudget(startDate, endDate)?.toModel())
        }catch (e: Exception){
            e("Error getting monthly budget", e)
            Resource.Failure(e.toLocalError())
        }
    }
}