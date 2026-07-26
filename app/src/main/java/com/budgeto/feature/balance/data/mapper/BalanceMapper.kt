package com.budgeto.feature.balance.data.mapper

import com.budgeto.core.database.entity.MonthlyBudgetEntity
import com.budgeto.core.utils.convertAmountToCents
import com.budgeto.core.utils.convertCentsToAmount
import com.budgeto.feature.balance.data.model.MonthlyBudgetModel
import com.budgeto.feature.balance.domain.entity.MonthlyBudget


fun MonthlyBudgetModel.toEntity() = MonthlyBudgetEntity(
    month = month,
    budgetCents = budgetCents
)
fun MonthlyBudgetEntity.toModel() = MonthlyBudgetModel(
    month = month,
    budgetCents = budgetCents
)

fun MonthlyBudget.toModel() = MonthlyBudgetModel(
    month = month,
    budgetCents = amount.convertAmountToCents()
)

fun MonthlyBudgetModel.toDomain() = MonthlyBudget(
    month = month,
    amount = budgetCents.convertCentsToAmount()
)