package com.zarabandajose.transcripto.core.common

/**
 * Utilidades para validación de datos de entrada.
 */
object ValidationUtils {
    
    /**
     * Valida que un texto no esté vacío.
     * @param text Texto a validar
     * @param fieldName Nombre del campo para el mensaje de error
     * @return Result con el texto validado o error
     */
    fun validateNonEmptyText(text: String, fieldName: String): Result<String> {
        return if (text.isBlank()) {
            Result.Error("$fieldName no puede estar vacío")
        } else {
            Result.Success(text)
        }
    }
    
    /**
     * Valida que una clave para Vigenère o XOR contenga al menos una letra.
     * @param key Clave a validar
     * @return Result con la clave validada o error
     */
    fun validateCipherKey(key: String): Result<String> {
        return when {
            key.isBlank() -> Result.Error("La clave no puede estar vacía")
            !key.any { it.isLetter() } -> Result.Error("La clave debe contener al menos una letra")
            else -> Result.Success(key)
        }
    }
    
    /**
     * Valida que un desplazamiento de César esté en el rango válido.
     * @param shift Desplazamiento a validar
     * @return Result con el desplazamiento validado o error
     */
    fun validateCaesarShift(shift: String): Result<Int> {
        return try {
            val shiftValue = shift.toInt()
            if (shiftValue in -25..25) {
                Result.Success(shiftValue)
            } else {
                Result.Error("El desplazamiento debe estar entre -25 y 25")
            }
        } catch (e: NumberFormatException) {
            Result.Error("El desplazamiento debe ser un número entero")
        }
    }
    
    /**
     * Normaliza una clave eliminando caracteres no alfabéticos y convirtiendo a mayúsculas.
     * @param key Clave a normalizar
     * @return Clave normalizada
     */
    fun normalizeKey(key: String): String {
        return key.filter { it.isLetter() }.uppercase()
    }
    
    /**
     * Valida que un salt no esté vacío cuando es requerido.
     * @param salt Salt a validar
     * @return Result con el salt validado o error
     */
    fun validateSalt(salt: String): Result<String> {
        return if (salt.isBlank()) {
            Result.Error("El salt no puede estar vacío")
        } else {
            Result.Success(salt)
        }
    }
}
