package com.zarabandajose.transcripto.data.crypto.cipher

import com.zarabandajose.transcripto.core.common.Result
import com.zarabandajose.transcripto.core.common.StringUtils
import com.zarabandajose.transcripto.core.common.ValidationUtils
import com.zarabandajose.transcripto.domain.model.CipherParams

/**
 * Implementación del cifrado Vigenère.
 * Opera sobre A-Z y a-z, ignora acentos, conserva mayúsculas/minúsculas,
 * y mantiene caracteres no alfabéticos sin cambio.
 */
class VigenereCipher {
    
    /**
     * Cifra texto usando el algoritmo Vigenère.
     */
    fun encrypt(input: String, params: CipherParams): Result<String> {
        val key = ValidationUtils.normalizeKey(params.getEffectiveKey())
        
        if (key.isEmpty()) {
            return Result.Error("La clave normalizada está vacía")
        }
        
        return Result.Success(processText(input, key, encrypt = true))
    }
    
    /**
     * Descifra texto usando el algoritmo Vigenère.
     */
    fun decrypt(input: String, params: CipherParams): Result<String> {
        val key = ValidationUtils.normalizeKey(params.getEffectiveKey())
        
        if (key.isEmpty()) {
            return Result.Error("La clave normalizada está vacía")
        }
        
        return Result.Success(processText(input, key, encrypt = false))
    }
    
    /**
     * Procesa el texto aplicando el algoritmo Vigenère.
     */
    private fun processText(text: String, key: String, encrypt: Boolean): String {
        val result = StringBuilder()
        var keyIndex = 0
        
        for (char in text) {
            if (StringUtils.isLatinLetter(char)) {
                val normalizedChar = StringUtils.normalizeChar(char)
                val isUpperCase = char.isUpperCase()
                
                // Obtener el valor de la clave
                val keyChar = key[keyIndex % key.length]
                val keyValue = keyChar - 'A'
                
                // Aplicar el cifrado/descifrado
                val baseChar = if (normalizedChar.isUpperCase()) 'A' else 'a'
                val charValue = normalizedChar.uppercaseChar() - 'A'
                
                val shiftedValue = if (encrypt) {
                    (charValue + keyValue) % 26
                } else {
                    (charValue - keyValue + 26) % 26
                }
                
                val resultChar = (baseChar + shiftedValue)
                
                // Mantener el caso original
                val finalChar = if (isUpperCase) resultChar.uppercaseChar() else resultChar.lowercaseChar()
                result.append(finalChar)
                
                keyIndex++
            } else {
                // Mantener caracteres no alfabéticos sin cambio
                result.append(char)
            }
        }
        
        return result.toString()
    }
}
