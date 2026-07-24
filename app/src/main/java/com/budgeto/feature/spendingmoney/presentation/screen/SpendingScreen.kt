package com.budgeto.feature.spendingmoney.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.budgeto.core.ui.spacing
import com.budgeto.core.utils.formatCurrency
import com.budgeto.feature.spendingmoney.domain.entity.Spending
import com.budgeto.feature.spendingmoney.domain.enums.CategoryType
import com.budgeto.feature.spendingmoney.domain.enums.SpendingType
import com.budgeto.feature.spendingmoney.presentation.intent.SpendingIntent
import com.budgeto.feature.spendingmoney.presentation.screen.component.spending.SpendingFieldDropdown
import com.budgeto.feature.spendingmoney.presentation.screen.component.spending.SpendingIconOption
import com.budgeto.feature.spendingmoney.presentation.screen.component.spending.SpendingIconPicker
import com.budgeto.feature.spendingmoney.presentation.viewmodel.SpendingViewModel

private val ButtonBottomInset = 72.dp

@Composable
fun SpendingScreen(
    modifier: Modifier = Modifier,
    onBackToSpendingScreen: () -> Unit,
    viewModel: SpendingViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var description by remember { mutableStateOf("") }
    var amount by remember {
        mutableStateOf(
            TextFieldValue("")
        )
    }
    var category by remember { mutableStateOf<CategoryType?>(null) }
    var spendingType by remember { mutableStateOf<SpendingType?>(null) }
    var icon by remember { mutableStateOf<SpendingIconOption?>(null) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.spaceMedium)
                .padding(bottom = ButtonBottomInset),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
        ) {
            SpendingMoneyDetailsToolBar(
                onBackToSpendingScreen = onBackToSpendingScreen
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

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

            SpendingFieldDropdown(
                label = "Category",
                options = CategoryType.entries,
                selectedOption = category,
                optionLabel = { it.type },
                onOptionSelected = { category = it }
            )

            SpendingFieldDropdown(
                label = "Spending",
                options = SpendingType.entries,
                selectedOption = spendingType,
                optionLabel = { it.type },
                onOptionSelected = { spendingType = it }
            )

            Text(
                text = "Icon",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            SpendingIconPicker(
                selectedIcon = icon,
                onIconSelected = { icon = it }
            )
        }

        Button(
            onClick = {
                viewModel.onIntent(
                    SpendingIntent.AddNewSpending(
                        Spending(
                            id = 0,
                            description = description,
                            amount = amount.text,
                            date = System.currentTimeMillis(),
                            category = category?.type.orEmpty(),
                            spendingType = spendingType?.type.orEmpty(),
                            icon = icon?.id ?: -1
                        )
                    )
                )
            },
            shape = MaterialTheme.shapes.small,
            enabled = !state.isLoading && description.isNotBlank() && amount.text.isNotBlank() && category != null && spendingType != null && icon != null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.spaceMedium)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(MaterialTheme.spacing.spaceMedium),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Add Spending")
            }
        }
    }
}

@Composable
fun SpendingMoneyDetailsToolBar(
    modifier: Modifier = Modifier,
    onBackToSpendingScreen: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onBackToSpendingScreen
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "Back"
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Add spending",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}