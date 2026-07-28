package com.budgeto.feature.balance.data.repository

import com.budgeto.core.error.DomainError
import com.budgeto.core.error.Resource
import com.budgeto.core.error.mapper.toDomainError
import com.budgeto.core.utils.fold
import com.budgeto.feature.balance.data.mapper.toDomain
import com.budgeto.feature.balance.data.mapper.toModel
import com.budgeto.feature.balance.data.service.BalanceAlertStateLocal
import com.budgeto.feature.balance.domain.enums.MonthlyBalanceAlert
import com.budgeto.feature.balance.domain.repository.BalanceAlertStateRepository
import javax.inject.Inject

class BalanceAlertStateRepositoryImpl @Inject constructor(
    private val balanceAlertStateLocal: BalanceAlertStateLocal
) : BalanceAlertStateRepository {

    override suspend fun getLastAlert(monthStart: Long): Resource<MonthlyBalanceAlert?, DomainError> {
        return balanceAlertStateLocal.getAlertState(monthStart).fold(
            onFailure = {
                Resource.Failure(it.toDomainError())
            },
            onSuccess = {
                Resource.Success(it?.toDomain())
            }
        )
    }

    override suspend fun saveLastAlert(
        monthStart: Long,
        alert: MonthlyBalanceAlert
    ): Resource<Unit, DomainError> {
        return balanceAlertStateLocal.upsertAlertState(alert.toModel(monthStart)).fold(
            onFailure = {
                Resource.Failure(it.toDomainError())
            },
            onSuccess = {
                Resource.Success(Unit)
            }
        )
    }
}
