package com.transcripto.data.crypto.cipher

import com.transcripto.domain.repository.CipherMethod
import com.transcripto.domain.model.CipherParams
import com.transcripto.domain.model.CipherResult
import javax.inject.Inject

/**
 * Implementation of the Caesar cipher algorithm.
 * Shifts each character by a specified key value.
 */
class CaesarCipher @Inject constructor() : CipherMethod {

    override fun getName(): String = "Caesar"

    override fun encrypt(input: String, params: CipherParams): CipherResult {
        if (input.isBlank()) {
            return CipherResult.Error("Text cannot be empty")
        }

        val shift = try {
            params.key?.toInt() ?: 0
        } catch (e: NumberFormatException) {
            return CipherResult.Error("Key must be a valid integer")
        }

        return try {
            val encrypted = input.map { char ->
                when {
                    char.isUpperCase() -> {
                        val shifted = ((char - 'A' + shift) % 26 + 26) % 26
                        'A' + shifted
                    }
                    char.isLowerCase() -> {
                        val shifted = ((char - 'a' + shift) % 26 + 26) % 26
                        'a' + shifted
                    }
                    else -> char
                }
            }.joinToString("")
            CipherResult.Success(encrypted)
        } catch (e: Exception) {
            CipherResult.Error("Encryption failed: ${e.message}")
        }
    }

    override fun decrypt(input: String, params: CipherParams): CipherResult {
        if (input.isBlank()) {
            return CipherResult.Error("Text cannot be empty")
        }

        val shift = try {
            params.key?.toInt() ?: 0
        } catch (e: NumberFormatException) {
            return CipherResult.Error("Key must be a valid integer")
        }

        return try {
            val decrypted = input.map { char ->
                when {
                    char.isUpperCase() -> {
                        val shifted = ((char - 'A' - shift) % 26 + 26) % 26
                        'A' + shifted
                    }
                    char.isLowerCase() -> {
                        val shifted = ((char - 'a' - shift) % 26 + 26) % 26
                        'a' + shifted
                    }
                    else -> char
                }
            }.joinToString("")
            CipherResult.Success(decrypted)
        } catch (e: Exception) {
            CipherResult.Error("Decryption failed: ${e.message}")
        }
    }
}