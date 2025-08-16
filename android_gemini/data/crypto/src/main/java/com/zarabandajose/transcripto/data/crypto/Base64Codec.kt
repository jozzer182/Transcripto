package com.zarabandajose.transcripto.data.crypto

import android.util.Base64
import com.zarabandajose.transcripto.domain.CipherMethod
import com.zarabandajose.transcripto.domain.DecryptionParams
import com.zarabandajose.transcripto.domain.EncryptionParams
import javax.inject.Inject

class Base64Codec @Inject constructor() : CipherMethod {
    override fun encrypt(input: String, params: EncryptionParams): String {
        val saltedInput = if (params.salt != null) "${params.salt}:$input" else input
        return Base64.encodeToString(saltedInput.toByteArray(), Base64.NO_WRAP)
    }

    override fun decrypt(input: String, params: DecryptionParams): String {
        val decoded = String(Base64.decode(input, Base64.NO_WRAP))
        return if (params.salt != null && decoded.startsWith("${params.salt}:")) {
            decoded.substringAfter(":")
        } else {
            decoded
        }
    }
}
