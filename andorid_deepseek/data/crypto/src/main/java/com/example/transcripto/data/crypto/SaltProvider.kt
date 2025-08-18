package com.example.transcripto.data.crypto

import java.security.SecureRandom
import javax.inject.Inject

class SaltProvider @Inject constructor() {
    private val secureRandom = SecureRandom()
    private val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    
    fun generate(length: Int = 12): String {
        require(length in 8..16) { "Salt length must be between 8 and 16 characters" }
        return (1..length)
            .map { chars[secureRandom.nextInt(chars.length)] }
            .joinToString("")
    }
}