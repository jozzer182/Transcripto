package com.zarabandajose.transcripto.data.crypto

import com.zarabandajose.transcripto.domain.cipher.CipherMethod
import com.zarabandajose.transcripto.domain.model.Method
import java.text.Normalizer

class VigenereCipher : CipherMethod {
    override val method: Method = Method.VIGENERE

    private fun normalizeKey(key: String): String {
        val noAccents = Normalizer.normalize(key, Normalizer.Form.NFD)
            .replace("\\p{M}+".toRegex(), "")
        return noAccents.filter { it.isLetter() }
    }

    private fun shiftForChar(k: Char): Int = when (k) {
        in 'A'..'Z' -> k.code - 'A'.code
        in 'a'..'z' -> k.code - 'a'.code
        else -> 0
    }

    private fun process(text: String, key: String, encrypt: Boolean): String {
        if (key.isEmpty()) error("Clave inválida")
        var ki = 0
        val out = StringBuilder(text.length)
        for (c in text) {
            if (c.isLetter()) {
                val shift = shiftForChar(key[ki % key.length])
                val s = if (encrypt) shift else -shift
                when (c) {
                    in 'A'..'Z' -> {
                        val base = 'A'.code
                        val idx = c.code - base
                        val n = (idx + s + 26 * 1000) % 26
                        out.append((n + base).toChar())
                        ki++
                    }
                    in 'a'..'z' -> {
                        val base = 'a'.code
                        val idx = c.code - base
                        val n = (idx + s + 26 * 1000) % 26
                        out.append((n + base).toChar())
                        ki++
                    }
                    else -> out.append(c)
                }
            } else {
                out.append(c)
            }
        }
        return out.toString()
    }

    override fun encrypt(input: String, params: CipherMethod.Params): String {
    val keyBase = params.key ?: error("Clave requerida")
    val salt = params.salt
    val effKey = normalizeKey(keyBase + if (params.useSalt && salt != null) String(salt) else "")
        return process(input, effKey, true)
    }

    override fun decrypt(input: String, params: CipherMethod.Params): String {
    val keyBase = params.key ?: error("Clave requerida")
    val salt = params.salt
    val effKey = normalizeKey(keyBase + if (params.useSalt && salt != null) String(salt) else "")
        return process(input, effKey, false)
    }
}
