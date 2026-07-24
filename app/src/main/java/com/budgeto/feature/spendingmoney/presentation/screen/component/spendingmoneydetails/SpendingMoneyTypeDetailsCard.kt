package com.budgeto.feature.spendingmoney.presentation.screen.component.spendingmoneydetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.budgeto.R
import com.budgeto.core.ui.spacing

@Composable
fun SpendingMoneyTypeDetailsCard(
    modifier: Modifier = Modifier,
    icon: Int,
    name: String,
    isSelected: Boolean
) {
    Card(
        modifier = modifier.padding(horizontal = MaterialTheme.spacing.spaceMedium),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 0.dp,
            color = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                Color.Transparent
        ),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.spacing.spaceMedium,
                    vertical = MaterialTheme.spacing.spaceSmall
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            when(icon){
                R.drawable.ic_insta_pay -> {
                    Image(
                        painter = painterResource(icon),
                        null,
                        modifier = modifier.size(24.dp),
                    )
                }
                else -> {
                    Image(
                        painter = painterResource(icon),
                        null,
                        modifier = modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                }
            }

            Text(
                modifier = Modifier.padding(start = MaterialTheme.spacing.spaceSmall),
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}