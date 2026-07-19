package com.echo.core.error


sealed interface DomainError : Error {
    enum class Local : DomainError {
        EMPTY_DATA,
        FILE_NOT_FOUND,
        INVALID_DATA,
    }
    enum class UNKNOWN : DomainError {
        UNKNOWN
    }
}