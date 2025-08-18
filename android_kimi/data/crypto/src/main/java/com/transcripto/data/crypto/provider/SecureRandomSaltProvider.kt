package com.transcripto.data.crypto.provider

import com.transcripto.domain.repository.SaltProvider
import java.security.SecureRandom
import java.util.Base64
import javax.inject.Inject

/**
 * Implementation of SaltProvider using SecureRandom for cryptographically secure salt generation.
 */
class SecureRandomSaltProvider @Inject constructor() : SaltProvider {
    
    private val secureRandom = SecureRandom()
    
    override fun generateSalt(length: Int): String {
        require(length > 0) { "Salt length must be positive" }
        
        val saltBytes = ByteArray(length)
        secureRandom.nextBytes(saltBytes)
        return Base64.getEncoder().encodeToString(saltBytes)
    }
}