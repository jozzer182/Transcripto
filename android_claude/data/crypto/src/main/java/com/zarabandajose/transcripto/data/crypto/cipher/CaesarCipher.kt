package com.zarabandajose.transcripto.data.crypto.cipher

import com.zarabandajose.transcripto.core.common.Result
import com.zarabandajose.transcripto.core.common.StringUtils
import com.zarabandajose.transcripto.domain.model.CipherParams

/**
 * Implementación del cifrado César.
 * Cifra solo letras latinas A-Z y a-z con wrap-around, manteniendo otros caracteres sin cambio.
 */
class CaesarCipher {
    
    /**
     * Cifra texto usando el algoritmo César.
     */
    fun encrypt(input: String, params: CipherParams): Result<String> {
        val shift = params.getEffectiveShift()
        return Result.Success(processText(input, shift))
    }
    
    /**
     * Descifra texto usando el algoritmo César.
     */
    fun decrypt(input: String, params: CipherParams): Result<String> {
        val shift = -params.getEffectiveShift() // Invertir el desplazamiento
        return Result.Success(processText(input, shift))
    }
    
    /**
     * Procesa el texto aplicando el desplazamiento César.
     */
    private fun processText(text: String, shift: Int): String {
        return text.map { char ->
            when {
                char in 'A'..'Z' -> {
                    val shifted = ((char - 'A' + shift) % 26 + 26) % 26
                    ('A' + shifted)
                }
                char in 'a'..'z' -> {
                    val shifted = ((char - 'a' + shift) % 26 + 26) % 26
                    ('a' + shifted)
                }
                else -> char // Mantener caracteres no alfabéticos sin cambio
            }
        }.joinToString("")
    }
}
