package com.budgeto.feature.balance.data.service

import com.budgeto.core.error.DataError
import com.budgeto.core.error.Resource
import com.budgeto.feature.balance.data.model.MonthlyAlertStateModel

interface BalanceAlertStateLocal {
    suspend fun getAlertState(monthStart: Long): Resource<MonthlyAlertStateModel?, DataError>
    suspend fun upsertAlertState(alertState: MonthlyAlertStateModel): Resource<Unit, DataError>
}
