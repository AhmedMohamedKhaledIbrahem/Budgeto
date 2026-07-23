package com.budgeto.feature.spendingmoney.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.budgeto.core.ui.spacing
import com.budgeto.feature.spendingmoney.domain.entity.Spending
import com.budgeto.feature.spendingmoney.domain.enums.CategoryType
import com.budgeto.feature.spendingmoney.domain.enums.SpendingType
import com.budgeto.feature.spendingmoney.presentation.intent.SpendingIntent
import com.budgeto.feature.spendingmoney.presentation.screen.component.SpendingFieldDropdown
import com.budgeto.feature.spendingmoney.presentation.screen.component.SpendingIconOption
import com.budgeto.feature.spendingmoney.presentation.screen.component.SpendingIconPicker
import com.budgeto.feature.spendingmoney.presentation.viewmodel.SpendingViewModel

@Composable
fun SpendingScreen(
    modifier: Modifier = Modifier,
    viewModel: SpendingViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf<CategoryType?>(null) }
    var spendingType by remember { mutableStateOf<SpendingType?>(null) }
    var icon by remember { mutableStateOf<SpendingIconOption?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(MaterialTheme.spacing.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium)
    ) {
        Text(
            text = "Add Spending",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
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
                if (newValue.all { it.isDigit() }) amount = newValue
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

        Button(
            onClick = {
                viewModel.onIntent(
                    SpendingIntent.AddNewSpending(
                        Spending(
                            id = 0,
                            description = description,
                            amount = amount,
                            date = System.currentTimeMillis(),
                            category = category?.type.orEmpty(),
                            spendingType = spendingType?.type.orEmpty(),
                            icon = icon?.id ?: -1
                        )
                    )
                )
            },
            enabled = !state.isLoading && description.isNotBlank() && amount.isNotBlank() && category != null && spendingType != null && icon != null,
            modifier = Modifier.fillMaxWidth()
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