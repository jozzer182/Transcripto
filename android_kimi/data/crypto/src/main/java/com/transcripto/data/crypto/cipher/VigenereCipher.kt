package com.transcripto.data.crypto.cipher

import com.transcripto.domain.cipher.CipherMethod
import com.transcripto.domain.model.CipherParams
import com.transcripto.domain.model.CipherResult
import javax.inject.Inject

/**
 * Implementation of the Vigenère cipher algorithm.
 * Uses a keyword for polyalphabetic substitution.
 */
class VigenereCipher @Inject constructor() : CipherMethod {

    override fun getName(): String = "Vigenere"

    override fun encrypt(params: CipherParams): CipherResult {
        if (params.text.isBlank()) {
            return CipherResult.Error("Text cannot be empty")
        }
        if (params.key.isNullOrBlank()) {
            return CipherResult.Error("Key cannot be empty")
        }

        val key = params.key.uppercase()
        val keyLength = key.length

        return try {
            val encrypted = params.text.mapIndexed { index, char ->
                if (char.isLetter()) {
                    val keyChar = key[index % keyLength]
                    val shift = keyChar - 'A'
                    val base = if (char.isUpperCase()) 'A' else 'a'
                    ((char - base + shift + 26) % 26 + base.code).toChar()
                } else {
                    char
                }
            }.joinToString("")
            CipherResult.Success(encrypted)
        } catch (e: Exception) {
            CipherResult.Error("Encryption failed: ${e.message}")
        }
    }

    override fun decrypt(params: CipherParams): CipherResult {
        if (params.text.isBlank()) {
            return CipherResult.Error("Text cannot be empty")
        }
        if (params.key.isNullOrBlank()) {
            return CipherResult.Error("Key cannot be empty")
        }

        val key = params.key.uppercase()
        val keyLength = key.length

        return try {
            val decrypted = params.text.mapIndexed { index, char ->
                if (char.isLetter()) {
                    val keyChar = key[index % keyLength]
                    val shift = keyChar - 'A'
                    val base = if (char.isUpperCase()) 'A' else 'a'
                    ((char - base - shift + 26) % 26 + base.code).toChar()
                } else {
                    char
                }
            }.joinToString("")
            CipherResult.Success(decrypted)
        } catch (e: Exception) {
            CipherResult.Error("Decryption failed: ${e.message}")
        }
    }
}