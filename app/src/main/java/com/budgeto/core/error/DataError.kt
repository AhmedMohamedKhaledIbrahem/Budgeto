package com.echo.core.error

interface DataError: Error {


    enum class Local : DataError {
        EMPTY_DATA,
        FILE_NOT_FOUND,
        INVALID_DATA,
    }
    enum class UNKNOWN : DataError {
        UNKNOWN
    }
}