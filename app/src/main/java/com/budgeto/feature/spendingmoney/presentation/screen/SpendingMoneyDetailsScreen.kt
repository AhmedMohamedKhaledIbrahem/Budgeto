package com.budgeto.feature.spendingmoney.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.budgeto.core.ui.spacing
import com.budgeto.core.utils.toFormattedTime
import com.budgeto.feature.spendingmoney.domain.entity.Spending
import com.budgeto.feature.spendingmoney.domain.enums.CategoryType
import com.budgeto.feature.spendingmoney.domain.enums.SpendingType
import com.budgeto.feature.spendingmoney.presentation.screen.component.spendingmoneydetails.AmountDetails
import com.budgeto.feature.spendingmoney.presentation.screen.component.spendingmoneydetails.SpendingMoneyCategoryDetailsCard
import com.budgeto.feature.spendingmoney.presentation.screen.component.spendingmoneydetails.SpendingMoneyDetailsCard
import com.budgeto.feature.spendingmoney.presentation.screen.component.spendingmoneydetails.SpendingMoneyDetailsToolBar
import com.budgeto.feature.spendingmoney.presentation.screen.component.spendingmoneydetails.SpendingMoneyTypeDetailsCard

@Composable
fun SpendingMoneyDetailsScreen(
    modifier: Modifier = Modifier,
    spending: Spending,
    onBackToSpendingScreen: () -> Unit
    ) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
    ) {
        SpendingMoneyDetailsToolBar(
            onBackToSpendingScreen = onBackToSpendingScreen
        )
        AmountDetails(
            amount = spending.amount,
        )
        SpendingMoneyDetailsCard(
            description = spending.description,
            time = spending.date.toFormattedTime()
        )
        SpendingMoneyCategoryDetailsGrid(categoryTypeSelected = CategoryType.getCategoryType(spending.category))
        SpendingMoneyTypeDetailsList(spendingType = SpendingType.getSpendingType(spending.spendingType))
    }
}

@Composable
fun SpendingMoneyCategoryDetailsGrid(
    modifier: Modifier = Modifier,
    categoryTypeSelected: CategoryType,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.spaceMedium),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = "Category",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(CategoryType.entries) { category ->
            val isSelectedCategory = category == categoryTypeSelected
            SpendingMoneyCategoryDetailsCard(
                isSelected = isSelectedCategory,
                icon = category.icon,
                name = category.type
            )
        }

    }
}

@Composable
fun SpendingMoneyTypeDetailsList(
    modifier: Modifier = Modifier,
    spendingType: SpendingType,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.spaceMedium),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = "Payment type",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(SpendingType.entries) { type ->
            val isSpendingTypeSelected = type == spendingType
            SpendingMoneyTypeDetailsCard(
                icon = type.icon,
                name = type.type,
                isSelected = isSpendingTypeSelected
            )
        }
    }

}





