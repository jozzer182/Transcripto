package com.zarabandajose.transcripto.domain.repository

/**
 * Interfaz para generar salts seguros.
 */
interface SaltRepository {
    
    /**
     * Genera un salt aleatorio seguro.
     * 
     * @param length Longitud del salt en bytes
     * @return Salt codificado en Base64
     */
    fun generateSalt(length: Int = 12): String
}
