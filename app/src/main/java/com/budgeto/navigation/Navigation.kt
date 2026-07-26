package com.budgeto.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.budgeto.core.event.UiEvent
import com.budgeto.core.event.combineEvent
import com.budgeto.core.navigation.Route
import com.budgeto.feature.balance.presentation.intent.BalanceIntent
import com.budgeto.feature.balance.presentation.screen.BalanceScreenRoot
import com.budgeto.feature.balance.presentation.viewmodel.BalanceViewModel
import com.budgeto.feature.spendingmoney.presentation.intent.SpendingIntent
import com.budgeto.feature.spendingmoney.presentation.screen.SpendingMoneyDetailsScreen
import com.budgeto.feature.spendingmoney.presentation.screen.SpendingMoneyScreen
import com.budgeto.feature.spendingmoney.presentation.screen.SpendingScreen
import com.budgeto.feature.spendingmoney.presentation.viewmodel.SpendingViewModel
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun Navigation() {
    val backStack = rememberNavBackStack(Route.HomeScreen)
    val viewModel = hiltViewModel<SpendingViewModel>()
    val balanceViewModel = hiltViewModel<BalanceViewModel>()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    fun navigateTo(route: Route) {
        val existingIndex = backStack.indexOf(route)
        if (existingIndex != -1) {
            while (backStack.size > existingIndex + 1) {
                backStack.removeAt(backStack.lastIndex)
            }
        } else {
            backStack.add(route)
        }
    }


    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.event.collect { event ->
                when (event) {
                    is UiEvent.CombineEvents -> combineEvent(
                        event = event.events,
                        onShowMessage = { message ->
                            launch {
                                snackbarHostState.showSnackbar(message.asString(context))
                            }
                        },
                        onNavigate = { route -> navigateTo(route) }
                    )

                    is UiEvent.ShowSnackBar -> {
                        launch {
                            snackbarHostState.showSnackbar(
                                event.message.asString(
                                    context
                                )
                            )
                        }
                    }
                    is UiEvent.Navigate -> navigateTo(event.route)
                    is UiEvent.BackToPreviousScreen -> {
                        backStack.removeAt(backStack.lastIndex)
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = when (snackbarData.visuals.actionLabel) {
                        "ERROR" -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.primary
                    },
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    actionColor = MaterialTheme.colorScheme.secondary
                )
            }
        },
        bottomBar = {
            if (
                backStack.last() is Route.HomeScreen ||
                backStack.last() is Route.AnalysisScreen ||
                backStack.last() is Route.BalanceScreen
            ) BottomNavigationBar(backStack)
        },
        floatingActionButton = {
            if (backStack.last() is Route.HomeScreen) {
                NewExpenseFab(
                    name = "Add Spending",
                    onclick = { viewModel.onIntent(SpendingIntent.AddNewSpendingClicked) }
                )
            }
            if (backStack.last() is Route.BalanceScreen) {
                NewExpenseFab(
                    name = "Add Budget Amount",
                    onclick = { balanceViewModel.onIntent(BalanceIntent.AddBalanceClicked) }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            backStack = backStack,
            entryProvider = entryProvider {
                entry<Route.HomeScreen> {
                    SpendingMoneyScreen(viewModel = viewModel)
                }
                entry<Route.SpendingScreen> {
                    SpendingScreen(
                        viewModel = viewModel,
                        onBackToSpendingScreen = {
                            viewModel.onIntent(
                                SpendingIntent.BackToSpendingScreen
                            )
                        }
                    )
                }
                entry<Route.SpendingDetailsScreen> {
                    SpendingMoneyDetailsScreen(
                        spending = it.spending, onBackToSpendingScreen = {
                            viewModel.onIntent(
                                SpendingIntent.BackToSpendingScreen
                            )
                        }
                    )
                }
                entry<Route.BalanceScreen> {
                    BalanceScreenRoot(viewModel = balanceViewModel)
                }
                entry<Route.AnalysisScreen> {

                }
            }
        )
    }
}