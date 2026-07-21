package com.budgeto.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.budgeto.core.navigation.Route

fun navigateToTab(
    backStack: NavBackStack<NavKey>,
    screen: Route
) {
    if (backStack.last() == screen) return
    backStack.clear()
    backStack.add(screen)
}