package com.zarabandajose.transcripto.domain.model

/**
 * Métodos de cifrado disponibles en la aplicación.
 */
enum class CipherMethod {
    CAESAR,
    BASE64,
    VIGENERE,
    XOR;
    
    /**
     * Convierte el enum a string para usar en el formato de envelope.
     */
    fun toEnvelopeString(): String = name
    
    companion object {
        /**
         * Convierte un string del formato envelope a CipherMethod.
         * @param value String del método
         * @return CipherMethod correspondiente o null si no es válido
         */
        fun fromEnvelopeString(value: String): CipherMethod? {
            return try {
                valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}
