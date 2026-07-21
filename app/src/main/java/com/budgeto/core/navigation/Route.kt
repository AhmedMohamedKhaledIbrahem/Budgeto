package com.budgeto.core.navigation

import androidx.navigation3.runtime.NavKey
import com.budgeto.core.ui.view.ViewEvent
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : ViewEvent, NavKey {
    @Serializable
    data object HomeScreen: Route,NavKey
    @Serializable
    data object AnalysisScreen:Route,NavKey
    @Serializable
    data object BalanceScreen:Route,NavKey
}