package com.transcripto.crypto

import java.security.SecureRandom
import kotlin.random.asKotlinRandom
import java.nio.charset.StandardCharsets

class SaltProvider {
    fun generate(): String {
        val random = SecureRandom().asKotlinRandom()
        val length = random.nextInt(8, 17)
        val bytes = ByteArray(length)
        random.nextBytes(bytes)
        return bytes.toString(StandardCharsets.UTF_8)
    }
}
