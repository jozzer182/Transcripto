package com.zarabandajose.transcripto.core.common

/**
 * Representa el resultado de una operación que puede fallar.
 * Encapsula el éxito o fallo de una operación de manera type-safe.
 */
sealed class Result<out T> {
    
    /**
     * Representa una operación exitosa con un valor.
     */
    data class Success<T>(val data: T) : Result<T>()
    
    /**
     * Representa una operación fallida con información del error.
     */
    data class Error(val message: String, val throwable: Throwable? = null) : Result<Nothing>()
    
    /**
     * Indica si el resultado es exitoso.
     */
    val isSuccess: Boolean
        get() = this is Success
    
    /**
     * Indica si el resultado es un error.
     */
    val isError: Boolean
        get() = this is Error
    
    /**
     * Obtiene el dato si es exitoso, o null si es error.
     */
    fun getOrNull(): T? = when (this) {
        is Success -> data
        is Error -> null
    }
    
    /**
     * Obtiene el dato si es exitoso, o lanza una excepción si es error.
     */
    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw throwable ?: Exception(message)
    }
    
    /**
     * Transforma el valor exitoso usando la función dada.
     */
    inline fun <R> map(transform: (T) -> R): Result<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
    }
    
    /**
     * Transforma el valor exitoso usando una función que retorna otro Result.
     */
    inline fun <R> flatMap(transform: (T) -> Result<R>): Result<R> = when (this) {
        is Success -> transform(data)
        is Error -> this
    }
    
    companion object {
        /**
         * Crea un Result a partir de una operación que puede lanzar excepción.
         */
        inline fun <T> runCatching(block: () -> T): Result<T> = try {
            Success(block())
        } catch (e: Exception) {
            Error(e.message ?: "Error desconocido", e)
        }
    }
}
