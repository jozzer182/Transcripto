package com.zarabandajose.transcripto.data.crypto

import com.zarabandajose.transcripto.domain.cipher.CipherMethod
import com.zarabandajose.transcripto.domain.model.Method

class CaesarCipher : CipherMethod {
    override val method: Method = Method.CAESAR

    private fun normalizeShift(shift: Int): Int {
        var s = shift % 26
        if (s < 0) s += 26
        return s
    }

    private fun hashSaltToShift(salt: ByteArray): Int {
        var h = 0
        for (b in salt) h = (31 * h + (b.toInt() and 0xFF))
        val mod = ((h % 26) + 26) % 26
        return mod
    }

    private fun transform(text: String, shift: Int): String {
        val s = normalizeShift(shift)
        val out = StringBuilder(text.length)
        for (c in text) {
            when (c) {
                in 'A'..'Z' -> {
                    val base = 'A'.code
                    val idx = c.code - base
                    val n = (idx + s) % 26
                    out.append((n + base).toChar())
                }
                in 'a'..'z' -> {
                    val base = 'a'.code
                    val idx = c.code - base
                    val n = (idx + s) % 26
                    out.append((n + base).toChar())
                }
                else -> out.append(c)
            }
        }
        return out.toString()
    }

    override fun encrypt(input: String, params: CipherMethod.Params): String {
    val baseShift = params.shift ?: error("Shift requerido")
    val salt = params.salt
    val extra = if (params.useSalt && salt != null) hashSaltToShift(salt) else 0
        return transform(input, baseShift + extra)
    }

    override fun decrypt(input: String, params: CipherMethod.Params): String {
    val baseShift = params.shift ?: error("Shift requerido")
    val salt = params.salt
    val extra = if (params.useSalt && salt != null) hashSaltToShift(salt) else 0
        return transform(input, -(baseShift + extra))
    }
}
