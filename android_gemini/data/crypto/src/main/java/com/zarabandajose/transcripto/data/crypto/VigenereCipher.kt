package com.zarabandajose.transcripto.data.crypto

import com.zarabandajose.transcripto.domain.CipherMethod
import com.zarabandajose.transcripto.domain.DecryptionParams
import com.zarabandajose.transcripto.domain.EncryptionParams
import javax.inject.Inject

class VigenereCipher @Inject constructor() : CipherMethod {
    override fun encrypt(input: String, params: EncryptionParams): String {
        val key = (params.key ?: "") + (params.salt ?: "")
        if (key.isEmpty() || key.none { it.isLetter() }) return input
        val keyStream = key.filter { it.isLetter() }.uppercase().iterator()
        return input.map { char ->
            if (char.isLetter()) {
                val keyChar = if (keyStream.hasNext()) keyStream.next() else key.filter { it.isLetter() }.uppercase().iterator().next()
                val shift = keyChar - 'A'
                when (char) {
                    in 'a'..'z' -> ((char - 'a' + shift) % 26 + 'a'.toInt()).toChar()
                    in 'A'..'Z' -> ((char - 'A' + shift) % 26 + 'A'.toInt()).toChar()
                    else -> char
                }
            } else {
                char
            }
        }.joinToString("")
    }

    override fun decrypt(input: String, params: DecryptionParams): String {
        val key = (params.key ?: "") + (params.salt ?: "")
        if (key.isEmpty() || key.none { it.isLetter() }) return input
        val keyStream = key.filter { it.isLetter() }.uppercase().iterator()
        return input.map { char ->
            if (char.isLetter()) {
                val keyChar = if (keyStream.hasNext()) keyStream.next() else key.filter { it.isLetter() }.uppercase().iterator().next()
                val shift = keyChar - 'A'
                when (char) {
                    in 'a'..'z' -> ((char - 'a' - shift + 26) % 26 + 'a'.toInt()).toChar()
                    in 'A'..'Z' -> ((char - 'A' - shift + 26) % 26 + 'A'.toInt()).toChar()
                    else -> char
                }
            } else {
                char
            }
        }.joinToString("")
    }
}
