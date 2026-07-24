package com.budgeto.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.budgeto.core.navigation.Route


@Composable
fun BottomNavigationBar(
    backStack: NavBackStack<NavKey>
) {
    val current = backStack.last()

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        NavigationBarItem(
            selected = current is Route.HomeScreen,
            onClick = { navigateToTab(backStack, Route.HomeScreen) },
            icon = {
                Icon(Icons.Default.Home, null)
            },
            colors = NavigationBarItemDefaults.materialThemeColorsScheme(),

            label = {
                Text("Home")
            }
        )
        NavigationBarItem(
            selected = current is Route.BalanceScreen,
            onClick = { navigateToTab(backStack, Route.BalanceScreen) },
            icon = {
                Icon(Icons.Default.AttachMoney, null)
            },
            colors = NavigationBarItemDefaults.materialThemeColorsScheme(),

            label = {
                Text("Balance")
            }
        )
        NavigationBarItem(
            selected = current is Route.AnalysisScreen,
            onClick = { navigateToTab(backStack, Route.AnalysisScreen) },
            icon = {
                Icon(Icons.Default.PieChart, null)
            },
            colors = NavigationBarItemDefaults.materialThemeColorsScheme(),
            label = {
                Text("Analysis")
            }
        )
    }
}

@Composable
private fun NavigationBarItemDefaults.materialThemeColorsScheme(): NavigationBarItemColors {
    return this.colors(
        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
        selectedTextColor = MaterialTheme.colorScheme.onSurface,
        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}