package com.budgeto.core.logger

import com.budgeto.BuildConfig
import timber.log.Timber
import javax.inject.Inject

/**
 * Abstraction for logging debug and error messages.
 *
 * This interface provides a simple contract for logging within the application,
 * allowing the underlying logging framework to be swapped without affecting
 * the rest of the codebase.
 *
 * Typical usage:
 * - Debug logs → [d]
 * - Error logs → [e]
 */
interface ReportLogger {
    /**
     * Logs a debug message.
     *
     * @param message The message to be logged.
     */
    fun d(message: String)

    /**
     * Logs a debug message with an associated [Throwable].
     *
     * @param message The message to be logged.
     * @param throwable The throwable associated with the log.
     */
    fun d(message: String, throwable: Throwable)

    /**
     * Logs an error message with an associated [Throwable].
     *
     * @param message The message to be logged.
     * @param throwable The throwable associated with the error.
     */
    fun e(message: String, throwable: Throwable)

}

class ReportLoggerImpl @Inject constructor() : ReportLogger {
    private inline fun debug(block: () -> Unit) {
        if (BuildConfig.DEBUG) block()
    }

    override fun d(message: String) = debug {
        Timber.d(message)
    }

    override fun d(message: String, throwable: Throwable) = debug {
        Timber.d(throwable, message)
    }

    override fun e(message: String, throwable: Throwable) = debug {
        Timber.e(throwable, message)
    }
}