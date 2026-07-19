package com.echo.core.error.mapper

import android.database.sqlite.SQLiteException
import com.echo.core.error.DataError
import com.echo.core.error.DomainError
import java.io.EOFException
import java.io.FileNotFoundException

fun DataError.toDomainError(): DomainError = when (this) {
    DataError.Local.EMPTY_DATA -> DomainError.Local.EMPTY_DATA
    DataError.Local.FILE_NOT_FOUND -> DomainError.Local.FILE_NOT_FOUND
    DataError.Local.INVALID_DATA -> DomainError.Local.INVALID_DATA
    DataError.UNKNOWN.UNKNOWN -> DomainError.UNKNOWN.UNKNOWN
    else -> DomainError.UNKNOWN.UNKNOWN
}


fun Exception.toLocalError(): DataError = when (this) {
    is FileNotFoundException -> DataError.Local.FILE_NOT_FOUND
    is EOFException -> DataError.Local.EMPTY_DATA
    is IllegalArgumentException, is SQLiteException -> DataError.Local.INVALID_DATA
    else -> DataError.UNKNOWN.UNKNOWN
}

