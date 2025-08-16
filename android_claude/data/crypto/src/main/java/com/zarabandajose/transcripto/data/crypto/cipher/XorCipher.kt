package com.zarabandajose.transcripto.data.crypto.cipher

import com.zarabandajose.transcripto.core.common.Result
import com.zarabandajose.transcripto.core.common.StringUtils
import com.zarabandajose.transcripto.domain.model.CipherParams
import java.util.Base64

/**
 * Implementación del cifrado XOR.
 * Opera byte a byte sobre UTF-8, el resultado cifrado se codifica en Base64.
 * Para descifrar, primero decodifica Base64 y luego aplica XOR.
 */
class XorCipher {
    
    /**
     * Cifra texto usando XOR y codifica el resultado en Base64.
     */
    fun encrypt(input: String, params: CipherParams): Result<String> {
        val key = params.getEffectiveKey()
        
        if (key.isEmpty()) {
            return Result.Error("La clave no puede estar vacía")
        }
        
        return try {
            val inputBytes = StringUtils.toUtf8Bytes(input)
            val keyBytes = StringUtils.toUtf8Bytes(key)
            
            val encryptedBytes = xorBytes(inputBytes, keyBytes)
            val base64Result = StringUtils.encodeBase64(encryptedBytes)
            
            Result.Success(base64Result)
        } catch (e: Exception) {
            Result.Error("Error al cifrar con XOR: ${e.message}", e)
        }
    }
    
    /**
     * Decodifica Base64 y descifra usando XOR.
     */
    fun decrypt(input: String, params: CipherParams): Result<String> {
        val key = params.getEffectiveKey()
        
        if (key.isEmpty()) {
            return Result.Error("La clave no puede estar vacía")
        }
        
        return try {
            val encryptedBytes = StringUtils.decodeBase64(input)
                ?: return Result.Error("Formato Base64 inválido")
            
            val keyBytes = StringUtils.toUtf8Bytes(key)
            
            val decryptedBytes = xorBytes(encryptedBytes, keyBytes)
            val result = StringUtils.fromUtf8Bytes(decryptedBytes)
            
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error("Error al descifrar con XOR: ${e.message}", e)
        }
    }
    
    /**
     * Aplica XOR entre los bytes del mensaje y la clave.
     * La clave se repite cíclicamente sobre la longitud del mensaje.
     */
    private fun xorBytes(messageBytes: ByteArray, keyBytes: ByteArray): ByteArray {
        val result = ByteArray(messageBytes.size)
        
        for (i in messageBytes.indices) {
            result[i] = (messageBytes[i].toInt() xor keyBytes[i % keyBytes.size].toInt()).toByte()
        }
        
        return result
    }
}
