package com.transcripto.data.crypto.cipher

import android.util.Base64
import com.transcripto.domain.cipher.CipherMethod
import com.transcripto.domain.model.CipherParams
import com.transcripto.domain.model.CipherResult
import javax.inject.Inject

/**
 * Implementation of Base64 encoding/decoding.
 * Note: Base64 is not encryption, just encoding. Key parameter is ignored.
 */
class Base64Codec @Inject constructor() : CipherMethod {

    override fun getName(): String = "Base64"

    override fun encrypt(input: String, params: CipherParams): CipherResult {
        if (input.isBlank()) {
            return CipherResult.Error("Text cannot be empty")
        }

        return try {
            val encoded = Base64.encodeToString(
                input.toByteArray(Charsets.UTF_8),
                Base64.NO_WRAP
            )
            CipherResult.Success(encoded)
        } catch (e: Exception) {
            CipherResult.Error("Encoding failed: ${e.message}")
        }
    }

    override fun decrypt(input: String, params: CipherParams): CipherResult {
        if (input.isBlank()) {
            return CipherResult.Error("Text cannot be empty")
        }

        return try {
            val decoded = Base64.decode(input, Base64.NO_WRAP)
            CipherResult.Success(String(decoded, Charsets.UTF_8))
        } catch (e: IllegalArgumentException) {
            CipherResult.Error("Invalid Base64 input")
        } catch (e: Exception) {
            CipherResult.Error("Decoding failed: ${e.message}")
        }
    }
}