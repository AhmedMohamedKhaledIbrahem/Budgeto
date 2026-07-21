package com.budgeto.core.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgeto.core.error.Resource
import com.budgeto.core.error.RootError
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

fun Long?.convertTimeStampToDate(): String {
    if (this == null) return "00/00/0000"
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(dateFormatter)
}

fun Long?.convertCentsToAmount(): String {
    val doubleValue = this?.toDouble() ?: return "0.00"
    val valueInUnits = doubleValue.div(100)
    return String.format(Locale.ENGLISH,"%.2f",valueInUnits)
}

fun String?.convertAmountToCents(): Long = (this?.toLong() ?: 0L) * 100

inline fun <D, E : RootError, R> Resource<D, E>.fold(
    onSuccess: (D) -> R,
    onFailure: (E) -> R,
): R = when (this) {
    is Resource.Success -> onSuccess(data)
    is Resource.Failure -> onFailure(error)
}
fun Long.toHeaderDateLabel(): String {
    val zoneId = ZoneId.systemDefault()
    val itemDate = Instant.ofEpochMilli(this).atZone(zoneId).toLocalDate()
    val today = LocalDate.now(zoneId)

    return when {
        itemDate.isEqual(today) -> "Today"
        itemDate.isEqual(today.minusDays(1)) -> "Yesterday"
        else -> itemDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault()))
    }
}

fun Long.toFormattedTime(): String {
    val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalTime()
        .format(formatter)
        .lowercase()
}

/**
 * Executes a suspend use case inside [viewModelScope] and handles its [Result].
 *
 * This function is designed to simplify calling suspend use cases from a [ViewModel]
 * by automatically launching a coroutine and delegating success and failure handling.
 *
 * @param D The type of successful result data.
 * @param E The type of error extending [RootError].
 * @param useCase A suspend lambda that returns a [Resource].
 * @param onSuccess Callback invoked when the result is [Resource.Success].
 * @param onFailure Callback invoked when the result is [Resource.Failure].
 */
inline fun <D, reified E : RootError> ViewModel.onUseCase(
    crossinline useCase: suspend () -> Resource<D, E>,
    crossinline onSuccess: suspend (D) -> Unit,
    crossinline onFailure: suspend (Resource.Failure<*, E>) -> Unit,
) {
    this.viewModelScope.launch {
        when (val result = useCase()) {
            is Resource.Success -> onSuccess(result.data)
            is Resource.Failure -> onFailure(result)
        }
    }
}