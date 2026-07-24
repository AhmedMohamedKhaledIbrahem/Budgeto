package com.budgeto.feature.spendingmoney.presentation.screen.component.spending

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.budgeto.core.ui.spacing
import com.budgeto.feature.spendingmoney.domain.enums.CategoryType
import com.budgeto.feature.spendingmoney.domain.enums.SpendingType

@Composable
fun SpendingDropdownMenu(
    modifier: Modifier = Modifier,
    onFilterSelected: (FilterSelection) -> Unit,
    isMenuExpanded: Boolean,
    onMenuExpandedChanged: (Boolean) -> Unit
) {
    DropdownMenu(
        expanded = isMenuExpanded,
        onDismissRequest = { onMenuExpandedChanged(false) }
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = "All Transactions",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            onClick = {
                onFilterSelected(FilterSelection.All)
                onMenuExpandedChanged(false)
            }
        )
        HorizontalDivider(modifier = modifier.padding(vertical = MaterialTheme.spacing.spaceSix))
        Text(
            text = "Categories type",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.spacing.spaceTwelve,
                vertical = MaterialTheme.spacing.spaceSix
            )
        )
        CategoryType.entries.forEach { categoryType ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = categoryType.type,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                onClick = {
                    onFilterSelected(FilterSelection.Category(categoryType))
                    onMenuExpandedChanged(false)
                }
            )
        }
        HorizontalDivider(modifier = modifier.padding(vertical = MaterialTheme.spacing.spaceSix))
        Text(
            text = "Spending type",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier.padding(
                horizontal = MaterialTheme.spacing.spaceTwelve,
                vertical = MaterialTheme.spacing.spaceSix
            )
        )
        SpendingType.entries.forEach { spending ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = spending.type,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                onClick = {
                    onFilterSelected(FilterSelection.PaymentType(spending))
                    onMenuExpandedChanged(false)
                }
            )
        }

    }
}


sealed interface FilterSelection {
    data object All : FilterSelection
    data class Category(val category: CategoryType) : FilterSelection
    data class PaymentType(val spendingType: SpendingType) : FilterSelection
}