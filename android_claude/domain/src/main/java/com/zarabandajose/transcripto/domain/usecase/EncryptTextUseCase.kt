package com.zarabandajose.transcripto.domain.usecase

import com.zarabandajose.transcripto.core.common.Result
import com.zarabandajose.transcripto.domain.model.CipherParams
import com.zarabandajose.transcripto.domain.repository.CipherRepository
import javax.inject.Inject

/**
 * Caso de uso para cifrar texto.
 * Encapsula la lógica de negocio para el cifrado incluyendo el formato de envelope.
 */
class EncryptTextUseCase @Inject constructor(
    private val cipherRepository: CipherRepository
) {
    
    /**
     * Cifra un texto y lo envuelve en el formato method=...;salt=...;payload=...
     * 
     * @param input Texto a cifrar
     * @param params Parámetros de cifrado
     * @return Result con el texto cifrado en formato envelope o error
     */
    suspend operator fun invoke(input: String, params: CipherParams): Result<String> {
        // Validar entrada
        if (input.isBlank()) {
            return Result.Error("El texto a cifrar no puede estar vacío")
        }
        
        if (!params.isValid()) {
            return Result.Error("Los parámetros de cifrado no son válidos")
        }
        
        // Realizar cifrado
        val encryptResult = cipherRepository.encrypt(input, params)
        
        return encryptResult.map { encryptedText ->
            // Crear envelope con metadatos
            buildEnvelope(
                method = params.method.toEnvelopeString(),
                salt = if (params.useSalt) params.salt else "",
                payload = encryptedText
            )
        }
    }
    
    /**
     * Construye el formato de envelope.
     */
    private fun buildEnvelope(method: String, salt: String, payload: String): String {
        val saltEncoded = if (salt.isNotEmpty()) {
            java.util.Base64.getEncoder().encodeToString(salt.toByteArray())
        } else {
            ""
        }
        
        return "method=$method;salt=$saltEncoded;payload=$payload"
    }
}
