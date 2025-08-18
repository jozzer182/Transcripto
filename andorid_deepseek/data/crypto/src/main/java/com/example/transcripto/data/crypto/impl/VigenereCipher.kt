package com.example.transcripto.data.crypto.impl

import com.example.transcripto.domain.crypto.CipherMethod
import com.example.transcripto.domain.crypto.CipherMethod.CipherParams
import javax.inject.Inject

class VigenereCipher @Inject constructor() : CipherMethod.Vigenere() {
    override fun encrypt(input: String, params: CipherParams): String {
        require(params.key.isNotEmpty()) { "Key is required for Vigenère cipher" }
        val effectiveKey = normalizeKey(params.key + params.salt)
        return input.mapIndexed { i, char ->
            when {
                char in 'A'..'Z' -> {
                    val shift = effectiveKey[i % effectiveKey.length] - 'A'
                    'A' + (char - 'A' + shift).mod(26)
                }
                char in 'a'..'z' -> {
                    val shift = effectiveKey[i % effectiveKey.length] - 'a'
                    'a' + (char - 'a' + shift).mod(26)
                }
                else -> char
            }
        }.joinToString("")
    }

    override fun decrypt(input: String, params: CipherParams): String {
        require(params.key.isNotEmpty()) { "Key is required for Vigenère cipher" }
        val effectiveKey = normalizeKey(params.key + params.salt)
        return input.mapIndexed { i, char ->
            when {
                char in 'A'..'Z' -> {
                    val shift = effectiveKey[i % effectiveKey.length] - 'A'
                    'A' + (char - 'A' - shift).mod(26)
                }
                char in 'a'..'z' -> {
                    val shift = effectiveKey[i % effectiveKey.length] - 'a'
                    'a' + (char - 'a' - shift).mod(26)
                }
                else -> char
            }
        }.joinToString("")
    }

    private fun normalizeKey(key: String): String {
        return key.filter { it.isLetter() }.takeIf { it.isNotEmpty() } 
            ?: throw IllegalArgumentException("Key must contain at least one letter")
    }
}