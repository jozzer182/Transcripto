package com.transcripto.domain.usecase

import com.transcripto.domain.repository.CipherMethod
import com.transcripto.domain.model.CipherParams
import com.transcripto.domain.model.CipherResult
import com.transcripto.domain.repository.CipherRepository
import com.transcripto.domain.repository.EnvelopeParser
import javax.inject.Inject

/**
 * Use case for decrypting text using various cipher methods.
 */
class DecryptText @Inject constructor(
    private val cipherRepository: CipherRepository,
    private val envelopeParser: EnvelopeParser
) {
    /**
     * Decrypts the input text using the specified method and parameters.
     * @param input The text to decrypt
     * @param params The decryption parameters
     * @return CipherResult containing decrypted text or error
     */
    operator fun invoke(input: String, params: CipherParams): CipherResult {
        if (input.isBlank()) {
            return CipherResult.Error("El texto de entrada no puede estar vacío")
        }

        val envelope = envelopeParser.parseEnvelope(input)
        
        val actualMethod = envelope?.method ?: params.method
        val actualParams = if (envelope != null) {
            params.copy(
                method = envelope.method,
                salt = envelope.salt ?: params.salt
            )
        } else {
            params
        }

        val cipherMethod = cipherRepository.getCipher(actualMethod)
            ?: return CipherResult.Error("Método de descifrado no soportado")

        val textToDecrypt = envelope?.payload ?: input

        return cipherMethod.decrypt(textToDecrypt, actualParams)
    }
}
