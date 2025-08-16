package com.transcripto.crypto

import com.transcripto.domain.CipherMethod

class CaesarCipher : CipherMethod {
    override fun encrypt(input: String, params: Map<String, Any>): String {
        val shift = (params["shift"] as? Int ?: 0) + getSaltShift(params["salt"] as? String ?: "", true)
        return input.map { char -> shiftChar(char, shift) }.joinToString("")
    }

    override fun decrypt(input: String, params: Map<String, Any>): String {
        val shift = (params["shift"] as? Int ?: 0) + getSaltShift(params["salt"] as? String ?: "", false)
        return input.map { char -> shiftChar(char, shift) }.joinToString("")
    }

    private fun shiftChar(c: Char, shift: Int): Char {
        val effectiveShift = shift % 26
        return when {
            c.isUpperCase() -> ((c - 'A' + effectiveShift + 26) % 26 + 'A'.code).toChar()
            c.isLowerCase() -> ((c - 'a' + effectiveShift + 26) % 26 + 'a'.code).toChar()
            else -> c
        }
    }

    private fun getSaltShift(salt: String, isEncrypt: Boolean): Int {
        if (salt.isEmpty()) return 0
        val hash = salt.hashCode() % 26
        return if (isEncrypt) hash else -hash
    }
}
