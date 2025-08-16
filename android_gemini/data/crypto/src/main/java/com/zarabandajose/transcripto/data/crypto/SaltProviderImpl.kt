package com.zarabandajose.transcripto.data.crypto

import android.util.Base64
import com.zarabandajose.transcripto.domain.SaltProvider
import java.security.SecureRandom
import javax.inject.Inject

class SaltProviderImpl @Inject constructor() : SaltProvider {
    override fun generateSalt(): String {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return Base64.encodeToString(salt, Base64.NO_WRAP)
    }
}
