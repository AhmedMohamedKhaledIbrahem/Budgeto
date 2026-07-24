package com.budgeto.feature.spendingmoney.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesomeMosaic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.budgeto.core.ui.component.LoadingDialog
import com.budgeto.core.ui.spacing
import com.budgeto.core.utils.getCurrentMonthRange
import com.budgeto.core.utils.toFormattedTime
import com.budgeto.feature.spendingmoney.domain.entity.GroupedSpending
import com.budgeto.feature.spendingmoney.domain.entity.Spending
import com.budgeto.feature.spendingmoney.domain.enums.CategoryType
import com.budgeto.feature.spendingmoney.domain.enums.SpendingType
import com.budgeto.feature.spendingmoney.presentation.intent.SpendingIntent
import com.budgeto.feature.spendingmoney.presentation.screen.component.FilterSelection
import com.budgeto.feature.spendingmoney.presentation.screen.component.SpendingDropdownMenu
import com.budgeto.feature.spendingmoney.presentation.screen.component.SpendingMoneyCard
import com.budgeto.feature.spendingmoney.presentation.viewmodel.SpendingViewModel

@Composable
fun SpendingMoneyScreen(
    modifier: Modifier = Modifier,
    viewModel: SpendingViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.onIntent(SpendingIntent.LoadSpending)
        val (start, end) = getCurrentMonthRange()
        viewModel.onIntent(SpendingIntent.TotalAmountByMonth(start, end))
    }
    if (state.isLoading) {
        LoadingDialog(show = true)
    } else {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            SpendingHeader(
                amount = state.totalAmount ?: "0.00",
                onFilterAll = { viewModel.onIntent(SpendingIntent.FilterAllSpending) },
                onFilterBySpending = { viewModel.onIntent(SpendingIntent.FilterBySpendingType(it)) },
                onFilterByCategory = { viewModel.onIntent(SpendingIntent.FilterByCategory(it)) }
            )
            SpendingList(
                groupedSpendings = state.groupedSpending,
                onSpendingMoneyClicked = {
                    viewModel.onIntent(
                        SpendingIntent.SpendingMoneyDetailsClicked(it)
                    )
                }
            )

        }
    }
}

@Composable
fun SpendingHeader(
    modifier: Modifier = Modifier,
    amount: String,
    onFilterAll: () -> Unit,
    onFilterBySpending: (SpendingType) -> Unit,
    onFilterByCategory: (CategoryType) -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.spaceMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier
                .padding(vertical = MaterialTheme.spacing.spaceMedium),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "History",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = amount.plus(" ").plus("EGP"),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Box {
            var isMenuExpanded by remember { mutableStateOf(false) }
            IconButton(
                onClick = { isMenuExpanded = true },
                colors = IconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    disabledContentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(Icons.Default.AutoAwesomeMosaic, contentDescription = null)
            }
            SpendingDropdownMenu(
                onFilterSelected = {
                    when (it) {
                        is FilterSelection.All -> {
                            onFilterAll()
                        }

                        is FilterSelection.Category -> {
                            onFilterByCategory(it.category)
                        }

                        is FilterSelection.PaymentType -> {
                            onFilterBySpending(it.spendingType)
                        }
                    }
                },
                isMenuExpanded = isMenuExpanded,
                onMenuExpandedChanged = { isMenuExpanded = it }
            )
        }
    }

}

@Composable
fun SpendingList(
    groupedSpendings: List<GroupedSpending>,
    onSpendingMoneyClicked: (spending: Spending) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(MaterialTheme.spacing.spaceExtraSmall),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceExtraSmall),
        modifier = Modifier.padding(MaterialTheme.spacing.spaceMedium)
    ) {
        if (groupedSpendings.isNotEmpty()) {
            groupedSpendings.forEach { group ->
                stickyHeader(key = group.header) {
                    Text(
                        text = group.header,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.spaceSmall)
                    )
                }
                items(items = group.items, key = { it.id }) { item ->
                    val formattedTime = remember(item.date) {
                        item.date.toFormattedTime()
                    }
                    SpendingMoneyCard(
                        description = item.description,
                        amount = item.amount,
                        time = formattedTime,
                        onSpendingMoneyClicked = {
                            onSpendingMoneyClicked(item)
                        }
                    )
                }
            }
        } else {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Spending Found",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.spaceMedium)
                    )
                }
            }
        }

    }


}