package com.zarabandajose.transcripto.data.crypto

import com.zarabandajose.transcripto.domain.CipherMethod
import com.zarabandajose.transcripto.domain.DecryptionParams
import com.zarabandajose.transcripto.domain.EncryptionParams
import javax.inject.Inject

class CaesarCipher @Inject constructor() : CipherMethod {
    override fun encrypt(input: String, params: EncryptionParams): String {
        val shift = params.key?.toIntOrNull() ?: 0
        val saltHash = params.salt?.hashCode() ?: 0
        val totalShift = (shift + saltHash) % 26
        return input.map { char ->
            when (char) {
                in 'a'..'z' -> ((char - 'a' + totalShift).let { if (it < 0) it + 26 else it } % 26 + 'a'.toInt()).toChar()
                in 'A'..'Z' -> ((char - 'A' + totalShift).let { if (it < 0) it + 26 else it } % 26 + 'A'.toInt()).toChar()
                else -> char
            }
        }.joinToString("")
    }

    override fun decrypt(input: String, params: DecryptionParams): String {
        val shift = params.key?.toIntOrNull() ?: 0
        val saltHash = params.salt?.hashCode() ?: 0
        val totalShift = (shift + saltHash) % 26
        return input.map { char ->
            when (char) {
                in 'a'..'z' -> ((char - 'a' - totalShift).let { if (it < 0) it + 26 else it } % 26 + 'a'.toInt()).toChar()
                in 'A'..'Z' -> ((char - 'A' - totalShift).let { if (it < 0) it + 26 else it } % 26 + 'A'.toInt()).toChar()
                else -> char
            }
        }.joinToString("")
    }
}
