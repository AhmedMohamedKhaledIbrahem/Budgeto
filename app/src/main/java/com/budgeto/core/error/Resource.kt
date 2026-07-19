package com.echo.core.error


typealias RootError = Error

sealed interface Resource<out D, out E : RootError> {
    data class Success<out D, E : RootError>(val data: D) : Resource<D, E>
    data class Failure<out D, E : RootError>(val error: E) : Resource<D, E>

}

