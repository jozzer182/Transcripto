package com.example.transcripto.domain.usecases

import com.example.transcripto.domain.crypto.CipherMethod
import com.example.transcripto.domain.crypto.CipherMethod.CipherParams
import javax.inject.Inject

class DetectAndParseEnvelope @Inject constructor() {
    operator fun invoke(input: String): Pair<CipherMethod, CipherParams>? {
        if (!input.startsWith("method=")) return null
        
        val parts = input.split(";")
        if (parts.size < 3) return null
        
        val method = when (parts[0].substringAfter("method=")) {
            "CAESAR" -> CipherMethod.Caesar
            "BASE64" -> CipherMethod.Base64
            "VIGENERE" -> CipherMethod.Vigenere
            "XOR" -> CipherMethod.Xor
            else -> return null
        }
        
        val salt = parts[1].substringAfter("salt=").takeIf { it.isNotEmpty() } ?: ""
        val payload = parts[2].substringAfter("payload=")
        
        return method to CipherParams(salt = salt)
    }
}