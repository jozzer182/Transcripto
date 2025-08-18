package com.transcripto.domain.usecase

import com.transcripto.domain.repository.CipherMethod
import com.transcripto.domain.model.CipherParams
import com.transcripto.domain.model.CipherResult
import com.transcripto.domain.repository.CipherRepository
import javax.inject.Inject

/**
 * Use case for encrypting text using various cipher methods.
 */
class EncryptText @Inject constructor(
    private val cipherRepository: CipherRepository
) {
    /**
     * Encrypts the input text using the specified method and parameters.
     * @param input The text to encrypt
     * @param params The encryption parameters
     * @return CipherResult containing encrypted text or error
     */
    operator fun invoke(input: String, params: CipherParams): CipherResult {
        if (input.isBlank()) {
            return CipherResult.Error("El texto de entrada no puede estar vacío")
        }

        val cipherMethod = cipherRepository.getCipher(params.method)
            ?: return CipherResult.Error("Método de cifrado no soportado")

        return cipherMethod.encrypt(input, params)
    }
}
