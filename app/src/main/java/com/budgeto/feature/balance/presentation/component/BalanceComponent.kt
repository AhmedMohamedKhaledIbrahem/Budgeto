package com.budgeto.feature.balance.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.budgeto.core.ui.spacing
import com.budgeto.core.utils.formatCurrency

@Composable
fun AddBalanceDialog(
    modifier: Modifier = Modifier,
    show: Boolean,
    onDismiss: () -> Unit,
    onAddClick: (amount: String) -> Unit
) {
    if (!show) return

    var amount by remember {
        mutableStateOf(
            TextFieldValue("")
        )
    }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(text = "Add Budget") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
            ) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { newValue ->
                        val digits = newValue.text.filter(Char::isDigit)

                        val formatted = formatCurrency(digits)

                        amount = TextFieldValue(
                            text = formatted,
                            selection = TextRange(formatted.length)
                        )
                    },
                    label = { Text("Amount") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAddClick( amount.text)
                }
            ) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}