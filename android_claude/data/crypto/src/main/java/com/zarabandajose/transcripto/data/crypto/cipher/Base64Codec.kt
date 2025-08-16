package com.zarabandajose.transcripto.data.crypto.cipher

import com.zarabandajose.transcripto.core.common.Result
import com.zarabandajose.transcripto.domain.model.CipherParams
import java.util.Base64

/**
 * Implementación del codec Base64.
 * Utiliza java.util.Base64 para encoding/decoding con soporte UTF-8.
 */
class Base64Codec {
    
    /**
     * Codifica texto a Base64, con soporte para salt.
     */
    fun encrypt(input: String, params: CipherParams): Result<String> {
        return try {
            val textToEncode = if (params.useSalt && params.salt.isNotEmpty()) {
                "${params.salt}:$input" // Preprend salt con separador
            } else {
                input
            }
            
            val encoded = Base64.getEncoder().encodeToString(textToEncode.toByteArray(Charsets.UTF_8))
            Result.Success(encoded)
        } catch (e: Exception) {
            Result.Error("Error al codificar Base64: ${e.message}", e)
        }
    }
    
    /**
     * Decodifica texto desde Base64, removiendo salt si está presente.
     */
    fun decrypt(input: String, params: CipherParams): Result<String> {
        return try {
            val decoded = String(Base64.getDecoder().decode(input), Charsets.UTF_8)
            
            val result = if (params.useSalt && params.salt.isNotEmpty()) {
                // Buscar y remover el salt del inicio
                val saltPrefix = "${params.salt}:"
                if (decoded.startsWith(saltPrefix)) {
                    decoded.substring(saltPrefix.length)
                } else {
                    // Si no encuentra el salt esperado, devolver el texto completo
                    decoded
                }
            } else {
                decoded
            }
            
            Result.Success(result)
        } catch (e: IllegalArgumentException) {
            Result.Error("Formato Base64 inválido", e)
        } catch (e: Exception) {
            Result.Error("Error al decodificar Base64: ${e.message}", e)
        }
    }
}
