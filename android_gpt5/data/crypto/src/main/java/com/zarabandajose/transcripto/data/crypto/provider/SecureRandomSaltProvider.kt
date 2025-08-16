package com.zarabandajose.transcripto.data.crypto.provider

import com.zarabandajose.transcripto.domain.provider.SaltProvider
import java.security.SecureRandom
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureRandomSaltProvider @Inject constructor(
    private val random: SecureRandom,
) : SaltProvider {
    override fun nextSalt(): ByteArray {
        val size = random.nextInt(16 - 8 + 1) + 8
        return ByteArray(size).also { random.nextBytes(it) }
    }
}
