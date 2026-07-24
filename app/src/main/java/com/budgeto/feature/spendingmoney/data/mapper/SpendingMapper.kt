package com.budgeto.feature.spendingmoney.data.mapper

import com.budgeto.core.database.entity.SpendingEntity
import com.budgeto.core.utils.convertAmountToCents
import com.budgeto.core.utils.convertCentsToAmount
import com.budgeto.core.utils.convertTimeStampToDate
import com.budgeto.feature.spendingmoney.data.model.SpendingModel
import com.budgeto.feature.spendingmoney.domain.entity.Spending


fun SpendingEntity.toModel() = SpendingModel(
    id = id,
    description = description,
    amountCents = amountCents,
    date = date,
    category = category,
    spendingType = spendingType,
    icon = icon
)

fun List<SpendingEntity>.toListModel(): List<SpendingModel> = map { it.toModel() }
fun SpendingModel.toEntity() = SpendingEntity(
    description = description,
    amountCents = amountCents,
    date = date,
    category = category,
    spendingType = spendingType,
    icon = icon
)

fun Spending.toModel() = SpendingModel(
    description = description,
    amountCents = amount.convertAmountToCents(),
    date = System.currentTimeMillis(),
    category = category,
    spendingType = spendingType,
    icon = icon
)

fun SpendingModel.toSpending() = Spending(
    id = id,
    description = description,
    amount = amountCents.convertCentsToAmount(),
    date = date,
    category = category,
    spendingType = spendingType
)
fun List<SpendingModel>.toListSpending(): List<Spending> = map { it.toSpending() }