package com.budgeto.feature.balance.data.service

import com.budgeto.core.database.dao.MonthlyAlertStateDao
import com.budgeto.core.error.DataError
import com.budgeto.core.error.Resource
import com.budgeto.core.error.mapper.toLocalError
import com.budgeto.core.logger.ReportLogger
import com.budgeto.feature.balance.data.mapper.toEntity
import com.budgeto.feature.balance.data.mapper.toModel
import com.budgeto.feature.balance.data.model.MonthlyAlertStateModel
import javax.inject.Inject

class BalanceAlertStateLocalImpl @Inject constructor(
    private val monthlyAlertStateDao: MonthlyAlertStateDao,
    reportLogger: ReportLogger
) : BalanceAlertStateLocal, ReportLogger by reportLogger {

    override suspend fun getAlertState(monthStart: Long): Resource<MonthlyAlertStateModel?, DataError> {
        return try {
            Resource.Success(monthlyAlertStateDao.getAlertState(monthStart)?.toModel())
        } catch (e: Exception) {
            e("Error getting monthly alert state", e)
            Resource.Failure(e.toLocalError())
        }
    }

    override suspend fun upsertAlertState(alertState: MonthlyAlertStateModel): Resource<Unit, DataError> {
        return try {
            monthlyAlertStateDao.upsertAlertState(alertState.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            e("Error saving monthly alert state", e)
            Resource.Failure(e.toLocalError())
        }
    }
}
