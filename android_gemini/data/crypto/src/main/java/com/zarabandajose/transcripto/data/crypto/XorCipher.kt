package com.zarabandajose.transcripto.data.crypto

import android.util.Base64
import com.zarabandajose.transcripto.domain.CipherMethod
import com.zarabandajose.transcripto.domain.DecryptionParams
import com.zarabandajose.transcripto.domain.EncryptionParams
import javax.inject.Inject

class XorCipher @Inject constructor() : CipherMethod {
    override fun encrypt(input: String, params: EncryptionParams): String {
        val key = (params.key ?: "") + (params.salt ?: "")
        if (key.isEmpty()) return input
        val keyBytes = key.toByteArray()
        val inputBytes = input.toByteArray()
        val result = ByteArray(inputBytes.size)
        for (i in inputBytes.indices) {
            result[i] = (inputBytes[i].toInt() xor keyBytes[i % keyBytes.size].toInt()).toByte()
        }
        return Base64.encodeToString(result, Base64.NO_WRAP)
    }

    override fun decrypt(input: String, params: DecryptionParams): String {
        val key = (params.key ?: "") + (params.salt ?: "")
        if (key.isEmpty()) return ""
        val keyBytes = key.toByteArray()
        val inputBytes = Base64.decode(input, Base64.NO_WRAP)
        val result = ByteArray(inputBytes.size)
        for (i in inputBytes.indices) {
            result[i] = (inputBytes[i].toInt() xor keyBytes[i % keyBytes.size].toInt()).toByte()
        }
        return String(result)
    }
}
