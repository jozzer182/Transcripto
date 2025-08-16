package com.zarabandajose.transcripto.core.common

import java.security.SecureRandom
import java.util.Base64

/**
 * Utilidades para operaciones con strings y encoding.
 */
object StringUtils {
    
    /**
     * Genera un salt aleatorio seguro.
     * @param length Longitud del salt en bytes (por defecto 12)
     * @return Salt codificado en Base64
     */
    fun generateSecureSalt(length: Int = 12): String {
        val bytes = ByteArray(length)
        SecureRandom().nextBytes(bytes)
        return Base64.getEncoder().encodeToString(bytes)
    }
    
    /**
     * Codifica bytes a Base64.
     * @param bytes Bytes a codificar
     * @return String en Base64
     */
    fun encodeBase64(bytes: ByteArray): String {
        return Base64.getEncoder().encodeToString(bytes)
    }
    
    /**
     * Decodifica una string Base64 a bytes.
     * @param encoded String en Base64
     * @return Bytes decodificados o null si hay error
     */
    fun decodeBase64(encoded: String): ByteArray? {
        return try {
            Base64.getDecoder().decode(encoded)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
    
    /**
     * Convierte string a bytes UTF-8.
     * @param text String a convertir
     * @return Bytes UTF-8
     */
    fun toUtf8Bytes(text: String): ByteArray {
        return text.toByteArray(Charsets.UTF_8)
    }
    
    /**
     * Convierte bytes UTF-8 a string.
     * @param bytes Bytes UTF-8
     * @return String resultante
     */
    fun fromUtf8Bytes(bytes: ByteArray): String {
        return String(bytes, Charsets.UTF_8)
    }
    
    /**
     * Calcula un hash simple para salt de César.
     * @param salt Salt a hashear
     * @return Valor hash
     */
    fun simpleHash(salt: String): Int {
        return salt.hashCode()
    }
    
    /**
     * Verifica si un carácter es una letra latina (A-Z, a-z).
     * @param char Carácter a verificar
     * @return true si es letra latina
     */
    fun isLatinLetter(char: Char): Boolean {
        return char in 'A'..'Z' || char in 'a'..'z'
    }
    
    /**
     * Normaliza un carácter a su equivalente sin acentos.
     * Simplificado para letras básicas.
     * @param char Carácter a normalizar
     * @return Carácter normalizado
     */
    fun normalizeChar(char: Char): Char {
        return when (char.lowercaseChar()) {
            'á', 'à', 'ä', 'â' -> 'a'
            'é', 'è', 'ë', 'ê' -> 'e'
            'í', 'ì', 'ï', 'î' -> 'i'
            'ó', 'ò', 'ö', 'ô' -> 'o'
            'ú', 'ù', 'ü', 'û' -> 'u'
            'ñ' -> 'n'
            'ç' -> 'c'
            else -> char
        }
    }
}
