package com.example.transcripto.data.crypto.impl

import com.example.transcripto.domain.crypto.CipherMethod
import com.example.transcripto.domain.crypto.CipherMethod.CipherParams
import javax.inject.Inject

class CaesarCipher @Inject constructor() : CipherMethod.Caesar() {
    override fun encrypt(input: String, params: CipherParams): String {
        val shift = params.shift + (if (params.salt.isNotEmpty()) params.salt.hashCode() % 26 else 0)
        return input.map { char ->
            when {
                char in 'A'..'Z' -> 'A' + (char - 'A' + shift).mod(26)
                char in 'a'..'z' -> 'a' + (char - 'a' + shift).mod(26)
                else -> char
            }
        }.joinToString("")
    }

    override fun decrypt(input: String, params: CipherParams): String {
        val shift = params.shift + (if (params.salt.isNotEmpty()) params.salt.hashCode() % 26 else 0)
        return input.map { char ->
            when {
                char in 'A'..'Z' -> 'A' + (char - 'A' - shift).mod(26)
                char in 'a'..'z' -> 'a' + (char - 'a' - shift).mod(26)
                else -> char
            }
        }.joinToString("")
    }
}