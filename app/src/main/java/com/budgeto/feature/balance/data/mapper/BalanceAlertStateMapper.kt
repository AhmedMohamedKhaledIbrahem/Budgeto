package com.budgeto.feature.balance.data.mapper

import com.budgeto.core.database.entity.MonthlyAlertStateEntity
import com.budgeto.feature.balance.data.model.MonthlyAlertStateModel
import com.budgeto.feature.balance.domain.enums.MonthlyBalanceAlert

fun MonthlyAlertStateModel.toEntity() = MonthlyAlertStateEntity(
    monthStart = monthStart,
    alertName = alertName
)

fun MonthlyAlertStateEntity.toModel() = MonthlyAlertStateModel(
    monthStart = monthStart,
    alertName = alertName
)

fun MonthlyAlertStateModel.toDomain(): MonthlyBalanceAlert =
    runCatching { MonthlyBalanceAlert.valueOf(alertName) }.getOrDefault(MonthlyBalanceAlert.UNKNOWN)

fun MonthlyBalanceAlert.toModel(monthStart: Long) = MonthlyAlertStateModel(
    monthStart = monthStart,
    alertName = name
)
