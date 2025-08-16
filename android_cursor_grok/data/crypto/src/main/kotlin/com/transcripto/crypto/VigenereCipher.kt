package com.transcripto.crypto

import com.transcripto.domain.CipherMethod

class VigenereCipher : CipherMethod {
    override fun encrypt(input: String, params: Map<String, Any>): String {
        val key = prepareKey(params["key"] as? String ?: "", params["salt"] as? String ?: "")
        if (key.isEmpty()) throw IllegalArgumentException("Clave inválida")
        return process(input, key, true)
    }

    override fun decrypt(input: String, params: Map<String, Any>): String {
        val key = prepareKey(params["key"] as? String ?: "", params["salt"] as? String ?: "")
        if (key.isEmpty()) throw IllegalArgumentException("Clave inválida")
        return process(input, key, false)
    }

    private fun prepareKey(key: String, salt: String): String {
        val effectiveKey = key + salt
        return effectiveKey.filter { it.isLetter() }
    }

    private fun process(text: String, key: String, encrypt: Boolean): String {
        val result = StringBuilder()
        var keyIndex = 0
        for (char in text) {
            if (char.isLetter()) {
                val shift = key[keyIndex % key.length].uppercaseChar().code - 'A'.code
                val shifted = shiftChar(char, if (encrypt) shift else -shift)
                result.append(shifted)
                keyIndex++
            } else {
                result.append(char)
            }
        }
        return result.toString()
    }

    private fun shiftChar(c: Char, shift: Int): Char {
        val base = if (c.isUpperCase()) 'A' else 'a'
        return ((c.code - base.code + shift + 26) % 26 + base.code).toChar()
    }
}
