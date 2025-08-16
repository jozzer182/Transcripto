package com.zarabandajose.transcripto.core.common

sealed class AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>()
    data class Error(val message: String, val cause: Throwable? = null) : AppResult<Nothing>()
}

inline fun <T> runCatchingResult(block: () -> T): AppResult<T> = try {
    AppResult.Success(block())
} catch (t: Throwable) {
    AppResult.Error(t.message ?: t::class.simpleName ?: "Error", t)
}
