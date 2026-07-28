package com.budgeto.feature.balance.domain.repository

import com.budgeto.core.error.DomainError
import com.budgeto.core.error.Resource
import com.budgeto.feature.balance.domain.enums.MonthlyBalanceAlert

interface BalanceAlertStateRepository {
    suspend fun getLastAlert(monthStart: Long): Resource<MonthlyBalanceAlert?, DomainError>
    suspend fun saveLastAlert(monthStart: Long, alert: MonthlyBalanceAlert): Resource<Unit, DomainError>
}
