package com.transcripto.crypto

import com.transcripto.domain.DetectAndParseEnvelopeUseCase
import com.transcripto.domain.Method
import java.util.Base64
import java.nio.charset.StandardCharsets

class DetectAndParseEnvelopeUseCaseImpl : DetectAndParseEnvelopeUseCase {
    override fun invoke(input: String): Map<String, Any>? {
        if (!input.startsWith("method=")) return null
        val parts = input.split(";")
        if (parts.size != 3) return null
        val methodStr = parts[0].substringAfter("=")
        val saltStr = parts[1].substringAfter("=")
        val payload = parts[2].substringAfter("=")
        val method = try { Method.valueOf(methodStr.uppercase()) } catch (e: Exception) { return null }
        val salt = if (saltStr.isNotEmpty()) String(Base64.getDecoder().decode(saltStr), StandardCharsets.UTF_8) else ""
        return mapOf("method" to method, "salt" to salt, "payload" to payload)
    }
}
