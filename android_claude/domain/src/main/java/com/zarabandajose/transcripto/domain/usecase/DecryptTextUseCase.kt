package com.zarabandajose.transcripto.domain.usecase

import com.zarabandajose.transcripto.core.common.Result
import com.zarabandajose.transcripto.domain.model.CipherParams
import com.zarabandajose.transcripto.domain.repository.CipherRepository
import javax.inject.Inject

/**
 * Caso de uso para descifrar texto.
 * Maneja tanto texto en formato envelope como texto directo.
 */
class DecryptTextUseCase @Inject constructor(
    private val cipherRepository: CipherRepository,
    private val detectAndParseEnvelopeUseCase: DetectAndParseEnvelopeUseCase
) {
    
    /**
     * Descifra un texto. Primero intenta detectar si está en formato envelope,
     * si no, usa los parámetros proporcionados.
     * 
     * @param input Texto a descifrar
     * @param params Parámetros de descifrado (usados si no hay envelope)
     * @return Result con el texto descifrado o error
     */
    suspend operator fun invoke(input: String, params: CipherParams): Result<String> {
        // Validar entrada
        if (input.isBlank()) {
            return Result.Error("El texto a descifrar no puede estar vacío")
        }
        
        // Intentar detectar envelope
        val envelopeResult = detectAndParseEnvelopeUseCase(input)
        
        return when (envelopeResult) {
            is Result.Success -> {
                // Usar datos del envelope
                val envelopeData = envelopeResult.data
                val envelopeParams = CipherParams(
                    method = envelopeData.method,
                    key = params.key, // Mantener la clave de UI
                    shift = params.shift, // Mantener el shift de UI
                    salt = envelopeData.salt,
                    useSalt = envelopeData.salt.isNotEmpty()
                )
                
                cipherRepository.decrypt(envelopeData.payload, envelopeParams)
            }
            
            is Result.Error -> {
                // No hay envelope, usar parámetros de UI
                if (!params.isValid()) {
                    return Result.Error("Los parámetros de descifrado no son válidos")
                }
                
                cipherRepository.decrypt(input, params)
            }
        }
    }
}
