package com.transcripto.domain.repository

import com.transcripto.domain.model.CipherParams
import com.transcripto.domain.model.CipherResult

/**
 * Interface that all cipher methods must implement.
 */
interface CipherMethod {
    /**
     * Encrypts the input text using the provided parameters.
     * @param input The text to encrypt
     * @param params The encryption parameters
     * @return CipherResult containing encrypted text or error
     */
    fun encrypt(input: String, params: CipherParams): CipherResult

    /**
     * Decrypts the input text using the provided parameters.
     * @param input The text to decrypt
     * @param params The decryption parameters
     * @return CipherResult containing decrypted text or error
     */
    fun decrypt(input: String, params: CipherParams): CipherResult

    /**
     * Returns the name of this cipher method.
     */
    fun getName(): String
}