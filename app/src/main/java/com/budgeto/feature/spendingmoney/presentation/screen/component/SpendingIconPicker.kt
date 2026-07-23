package com.budgeto.feature.spendingmoney.presentation.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.budgeto.R
import com.budgeto.core.ui.spacing

@Composable
fun SpendingIconPicker(
    modifier: Modifier = Modifier,
    selectedIcon: SpendingIconOption?,
    onIconSelected: (SpendingIconOption) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
    ) {
        SpendingIconOption.entries.forEach { option ->
            val isSelected = option == selectedIcon
            Box(
                modifier = Modifier
                    .size(MaterialTheme.spacing.spaceExtraLarge)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surfaceContainer
                    )
                    .border(
                        width = if (isSelected) 2.dp else 0.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = CircleShape
                    )
                    .selectable(selected = isSelected, onClick = { onIconSelected(option) }),
                contentAlignment = Alignment.Center
            ) {
                when (option) {
                    SpendingIconOption.WALLET -> Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = option.label,
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    SpendingIconOption.DEBIT_CARD -> Icon(
                        imageVector = Icons.Default.CreditCard,
                        contentDescription = option.label,
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    SpendingIconOption.CREDIT_CARD -> Icon(
                        imageVector = Icons.Default.Payments,
                        contentDescription = option.label,
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    SpendingIconOption.INSTA_PAY -> Image(
                        painter = painterResource(R.drawable.ic_insta_pay),
                        contentDescription = option.label,
                        modifier = Modifier.padding(MaterialTheme.spacing.spaceExtraSmall)
                    )
                }
            }
        }
    }
}