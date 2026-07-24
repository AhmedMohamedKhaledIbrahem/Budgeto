package com.budgeto.core.error.mapper

import com.budgeto.R
import com.budgeto.core.error.DomainError
import com.budgeto.core.error.DomainError.Local.EMPTY_DATA
import com.budgeto.core.error.DomainError.Local.FILE_NOT_FOUND
import com.budgeto.core.error.DomainError.Local.INVALID_DATA
import com.budgeto.core.error.DomainError.UNKNOWN.UNKNOWN
import com.budgeto.core.error.Resource
import com.budgeto.core.error.RootError
import com.budgeto.core.ui.UiText
import com.budgeto.core.ui.UiText.DynamicString
import com.budgeto.core.ui.UiText.StringResource

fun DomainError.asUiText(): UiText {
    return when (this) {

        EMPTY_DATA -> StringResource(R.string.error_empty_data)
        FILE_NOT_FOUND -> StringResource(R.string.error_file_not_found)
        INVALID_DATA -> StringResource(R.string.error_invalid_data)
        UNKNOWN -> StringResource(R.string.error_unknown)
    }
}

inline fun <reified E : RootError> Resource.Failure<*, E>.asUiTextOrDefault(
    default: UiText = DynamicString(""),
): UiText {
    return when (val error = this.error) {
        is DomainError -> error.asUiText()
        else -> default
    }
}