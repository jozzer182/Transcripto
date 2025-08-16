package com.zarabandajose.transcripto.domain.model

/**
 * Parámetros de configuración para las operaciones de cifrado.
 * 
 * @param method Método de cifrado a utilizar
 * @param key Clave para Vigenère y XOR, ignorada para otros métodos
 * @param shift Desplazamiento para César, ignorado para otros métodos
 * @param salt Salt opcional para todos los métodos
 * @param useSalt Indica si se debe usar salt
 */
data class CipherParams(
    val method: CipherMethod,
    val key: String = "",
    val shift: Int = 0,
    val salt: String = "",
    val useSalt: Boolean = false
) {
    
    /**
     * Obtiene la clave efectiva combinando la clave base con el salt si está habilitado.
     * Solo aplica para Vigenère y XOR.
     */
    fun getEffectiveKey(): String {
        return if (useSalt && salt.isNotEmpty() && method in listOf(CipherMethod.VIGENERE, CipherMethod.XOR)) {
            key + salt
        } else {
            key
        }
    }
    
    /**
     * Obtiene el desplazamiento efectivo para César incluyendo el hash del salt si está habilitado.
     */
    fun getEffectiveShift(): Int {
        return if (useSalt && salt.isNotEmpty() && method == CipherMethod.CAESAR) {
            shift + (salt.hashCode() % 26)
        } else {
            shift
        }
    }
    
    /**
     * Valida que los parámetros sean correctos para el método seleccionado.
     * @return true si los parámetros son válidos
     */
    fun isValid(): Boolean {
        return when (method) {
            CipherMethod.CAESAR -> shift in -25..25
            CipherMethod.BASE64 -> true
            CipherMethod.VIGENERE, CipherMethod.XOR -> key.isNotBlank() && key.any { it.isLetter() }
        }
    }
}
