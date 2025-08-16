package com.transcripto.crypto

import com.transcripto.domain.CipherMethod
import java.util.Base64
import java.nio.charset.StandardCharsets

class XorCipher : CipherMethod {
    override fun encrypt(input: String, params: Map<String, Any>): String {
        val key = (params["key"] as? String ?: "") + (params["salt"] as? String ?: "")
        if (key.isEmpty()) throw IllegalArgumentException("Clave requerida")
        val inputBytes = input.toByteArray(StandardCharsets.UTF_8)
        val keyBytes = key.toByteArray(StandardCharsets.UTF_8)
        val result = xorBytes(inputBytes, keyBytes)
        return Base64.getEncoder().encodeToString(result)
    }

    override fun decrypt(input: String, params: Map<String, Any>): String {
        val key = (params["key"] as? String ?: "") + (params["salt"] as? String ?: "")
        if (key.isEmpty()) throw IllegalArgumentException("Clave requerida")
        val inputBytes = Base64.getDecoder().decode(input)
        val keyBytes = key.toByteArray(StandardCharsets.UTF_8)
        val result = xorBytes(inputBytes, keyBytes)
        return result.toString(StandardCharsets.UTF_8)
    }

    private fun xorBytes(data: ByteArray, key: ByteArray): ByteArray {
        val result = ByteArray(data.size)
        for (i in data.indices) {
            result[i] = (data[i].toInt() xor key[i % key.size].toInt()).toByte()
        }
        return result
    }
}
