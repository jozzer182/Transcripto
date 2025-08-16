package com.transcripto.crypto

import com.transcripto.domain.CipherMethod
import java.util.Base64
import java.nio.charset.StandardCharsets

class Base64Codec : CipherMethod {
    override fun encrypt(input: String, params: Map<String, Any>): String {
        val salt = params["salt"] as? String ?: ""
        val text = if (salt.isNotEmpty()) "$salt:$input" else input
        return Base64.getEncoder().encodeToString(text.toByteArray(StandardCharsets.UTF_8))
    }

    override fun decrypt(input: String, params: Map<String, Any>): String {
        val decoded = Base64.getDecoder().decode(input).toString(StandardCharsets.UTF_8)
        val salt = params["salt"] as? String ?: ""
        return if (salt.isNotEmpty() && decoded.startsWith("$salt:")) decoded.substring(salt.length + 1) else decoded
    }
}
