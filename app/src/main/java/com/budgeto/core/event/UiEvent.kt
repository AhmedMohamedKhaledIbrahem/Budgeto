package com.budgeto.core.event

import com.budgeto.core.navigation.Route
import com.budgeto.core.ui.UiText
import com.budgeto.core.ui.view.ViewEvent

typealias ShowSnackBarEvent = UiEvent.ShowSnackBar
typealias Navigation = UiEvent.Navigate

sealed interface UiEvent : ViewEvent {
    data class ShowSnackBar(val message: UiText) : UiEvent
    data class Navigate(val route: Route) : UiEvent
    data class CombineEvents(val events: List<ViewEvent>) : UiEvent
}