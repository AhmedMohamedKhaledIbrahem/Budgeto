package com.budgeto.feature.spendingmoney.presentation.screen.component.spendingmoneydetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.budgeto.core.ui.spacing

@Composable
fun AmountDetails(
    modifier: Modifier = Modifier,
    amount: String,
    currency: String = "EGP"
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.spaceMedium)

    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Amount",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.spaceMedium)

    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = amount.plus(" ").plus(currency),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }

}