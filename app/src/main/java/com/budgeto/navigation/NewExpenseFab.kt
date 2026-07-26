package com.budgeto.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun NewExpenseFab(
    onclick: () -> Unit,
    name: String
) {
    ExtendedFloatingActionButton(
        onClick = onclick,
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        },
        text = {
            Text(text = name)
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
}