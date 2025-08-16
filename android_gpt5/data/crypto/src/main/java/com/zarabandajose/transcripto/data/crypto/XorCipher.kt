package com.zarabandajose.transcripto.data.crypto

import com.zarabandajose.transcripto.domain.cipher.CipherMethod
import com.zarabandajose.transcripto.domain.model.Method
import java.nio.charset.StandardCharsets
import java.util.Base64

class XorCipher : CipherMethod {
    override val method: Method = Method.XOR

    private fun xorBytes(message: ByteArray, key: ByteArray): ByteArray {
        val out = ByteArray(message.size)
        for (i in message.indices) {
            out[i] = (message[i].toInt() xor key[i % key.size].toInt()).toByte()
        }
        return out
    }

    override fun encrypt(input: String, params: CipherMethod.Params): String {
    val keyBase = params.key?.toByteArray(StandardCharsets.UTF_8) ?: error("Clave requerida")
    val salt = params.salt
    val effKey = if (params.useSalt && salt != null) keyBase + salt else keyBase
        val xored = xorBytes(input.toByteArray(StandardCharsets.UTF_8), effKey)
        return Base64.getEncoder().encodeToString(xored)
    }

    override fun decrypt(input: String, params: CipherMethod.Params): String {
    val keyBase = params.key?.toByteArray(StandardCharsets.UTF_8) ?: error("Clave requerida")
    val salt = params.salt
    val effKey = if (params.useSalt && salt != null) keyBase + salt else keyBase
        val decoded = Base64.getDecoder().decode(input)
        val xored = xorBytes(decoded, effKey)
        return String(xored, StandardCharsets.UTF_8)
    }
}
