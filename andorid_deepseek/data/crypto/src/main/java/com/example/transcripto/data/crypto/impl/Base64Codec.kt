package com.example.transcripto.data.crypto.impl

import com.example.transcripto.domain.crypto.CipherMethod
import com.example.transcripto.domain.crypto.CipherMethod.CipherParams
import java.util.Base64
import javax.inject.Inject

class Base64Codec @Inject constructor() : CipherMethod.Base64() {
    override fun encrypt(input: String, params: CipherParams): String {
        val bytes = if (params.salt.isNotEmpty()) {
            "${params.salt}:$input".toByteArray()
        } else {
            input.toByteArray()
        }
        return Base64.getEncoder().encodeToString(bytes)
    }

    override fun decrypt(input: String, params: CipherParams): String {
        val decoded = String(Base64.getDecoder().decode(input))
        return if (params.salt.isNotEmpty() && decoded.startsWith("${params.salt}:")) {
            decoded.substringAfter(":")
        } else {
            decoded
        }
    }
}