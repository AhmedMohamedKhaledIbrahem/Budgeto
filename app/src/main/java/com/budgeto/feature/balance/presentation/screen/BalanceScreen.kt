package com.budgeto.feature.balance.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.budgeto.core.ui.component.LoadingDialog
import com.budgeto.core.ui.spacing
import com.budgeto.core.utils.convertAmountToCents
import com.budgeto.core.utils.convertCentsToAmount
import com.budgeto.core.utils.getCurrentMonthRange
import com.budgeto.core.utils.toFormattedTime
import com.budgeto.feature.balance.domain.entity.MonthlyBudget
import com.budgeto.feature.balance.domain.enums.MonthlyBalanceAlert
import com.budgeto.feature.balance.presentation.component.AddBalanceDialog
import com.budgeto.feature.balance.presentation.intent.BalanceIntent
import com.budgeto.feature.balance.presentation.viewmodel.BalanceViewModel


@Composable
fun BalanceScreenRoot(
    viewModel: BalanceViewModel
) {
    LaunchedEffect(Unit) {
        val (start, end) = getCurrentMonthRange()
        viewModel.onIntent(BalanceIntent.CalculateMonthlyBalance(start, end))
    }
    BalanceScreen(viewModel = viewModel)
}

@Composable
fun BalanceScreen(
    modifier: Modifier = Modifier,
    viewModel: BalanceViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    if (state.isLoading) {
        LoadingDialog(
            show = true,
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = MaterialTheme.spacing.spaceMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {
            MonthlyBalanceIndicator(
                amount = state.spent,
                maxAmount = state.monthlyBudget?.amount.convertAmountToCents(),
                monthlyBalanceAlert = state.alert
            )
            BalanceCardDetails(
                date = state.monthlyBudget?.month?.toFormattedTime() ?: System.currentTimeMillis()
                    .toFormattedTime(),
                budgetAmountMonthly = state.monthlyBudget?.amount ?: "0.00",
                spentAmountMonthly = state.spent.convertCentsToAmount()
            )
        }
    }

    AddBalanceDialog(
        show = state.isDialogVisible,
        onDismiss = { viewModel.onIntent(BalanceIntent.DismissBalanceDialog) },
        onAddClick = { amount ->
            viewModel.onIntent(
                BalanceIntent.InsertBalance(
                    monthlyBudget = MonthlyBudget(
                        month = System.currentTimeMillis(),
                        amount = amount
                    )
                )
            )
        }
    )
}

@Composable
fun MonthlyBalanceIndicator(
    modifier: Modifier = Modifier,
    amount: Long,
    maxAmount: Long,
    monthlyBalanceAlert: MonthlyBalanceAlert,
    currency: String = "EGP",
) {
    val progress = (amount.toFloat() / maxAmount.toFloat()).coerceIn(0f, 1f)
    val rememberProgress by rememberUpdatedState(progress)
    Box(
        modifier = modifier.size(180.dp),
        contentAlignment = Center
    ) {
        CircularProgressIndicator(
            progress = { rememberProgress },
            modifier = modifier.fillMaxSize(),
            color = when {
                progress >= 0.9f -> Color(monthlyBalanceAlert.color)
                progress >= 0.7f -> Color(monthlyBalanceAlert.color)
                else -> Color(monthlyBalanceAlert.color)
            },
            strokeWidth = 12.dp,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
        )

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                modifier = modifier.basicMarquee(),
                text = amount.convertCentsToAmount().plus(" ").plus(currency),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = modifier.basicMarquee(),
                text = "of ${maxAmount.convertCentsToAmount()}".plus(" ").plus(currency),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun BalanceCardDetails(
    modifier: Modifier = Modifier,
    date: String,
    budgetAmountMonthly: String,
    spentAmountMonthly: String,
    currency: String = "EGP"
) {
    Card(
        modifier = modifier.padding(horizontal = MaterialTheme.spacing.spaceMedium),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.spacing.spaceSmall
        ),
    ) {
        Column(
            modifier.padding(MaterialTheme.spacing.spaceMedium)
        ) {
            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier.padding(vertical = MaterialTheme.spacing.spaceExtraSmall))
            Row(
                modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Spent Amount Monthly",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = spentAmountMonthly.plus(" ").plus(currency),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier.padding(vertical = MaterialTheme.spacing.spaceExtraSmall))
            Row(
                modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Budget Amount Monthly",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = budgetAmountMonthly.plus(" ").plus(currency),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}