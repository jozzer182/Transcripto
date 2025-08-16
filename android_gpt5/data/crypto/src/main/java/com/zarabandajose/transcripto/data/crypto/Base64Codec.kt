package com.zarabandajose.transcripto.data.crypto

import com.zarabandajose.transcripto.domain.cipher.CipherMethod
import com.zarabandajose.transcripto.domain.model.Method
import java.nio.charset.StandardCharsets
import java.util.Base64

class Base64Codec : CipherMethod {
    override val method: Method = Method.BASE64

    override fun encrypt(input: String, params: CipherMethod.Params): String {
        val salt = params.salt
        val withSalt = if (params.useSalt && salt != null) {
            val saltStr = String(salt, StandardCharsets.UTF_8)
            "$saltStr:$input"
        } else input
        return Base64.getEncoder().encodeToString(withSalt.toByteArray(StandardCharsets.UTF_8))
    }

    override fun decrypt(input: String, params: CipherMethod.Params): String {
        val decoded = String(Base64.getDecoder().decode(input), StandardCharsets.UTF_8)
        val salt = params.salt
        if (params.useSalt && salt != null) {
            val saltStr = String(salt, StandardCharsets.UTF_8)
            val prefix = "$saltStr:"
            return if (decoded.startsWith(prefix)) decoded.removePrefix(prefix) else decoded
        }
        return decoded
    }
}
