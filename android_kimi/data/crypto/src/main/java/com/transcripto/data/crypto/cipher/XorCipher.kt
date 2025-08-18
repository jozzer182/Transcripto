package com.transcripto.data.crypto.cipher

import android.util.Base64
import com.transcripto.domain.cipher.CipherMethod
import com.transcripto.domain.model.CipherParams
import com.transcripto.domain.model.CipherResult
import javax.inject.Inject

/**
 * Implementation of the XOR cipher algorithm.
 * Uses bitwise XOR operation for encryption/decryption.
 */
class XorCipher @Inject constructor() : CipherMethod {

    override fun getName(): String = "Xor"

    override fun encrypt(params: CipherParams): CipherResult {
        if (params.text.isBlank()) {
            return CipherResult.Error("Text cannot be empty")
        }

        if (params.key.isNullOrBlank()) {
            return CipherResult.Error("Key cannot be empty")
        }

        return try {
            val keyBytes = params.key!!.toByteArray(Charsets.UTF_8)
            val textBytes = params.text.toByteArray(Charsets.UTF_8)

            val encrypted = textBytes.mapIndexed { index, byte ->
                byte xor keyBytes[index % keyBytes.size]
            }.toByteArray()

            // Encode as Base64 to ensure safe storage/transmission
            val encoded = Base64.encodeToString(
                encrypted,
                Base64.NO_WRAP
            )
            CipherResult.Success(encoded)
        } catch (e: Exception) {
            CipherResult.Error("Encryption failed: ${e.message}")
        }
    }

    override fun decrypt(params: CipherParams): CipherResult {
        if (params.text.isBlank()) {
            return CipherResult.Error("Text cannot be empty")
        }

        if (params.key.isNullOrBlank()) {
            return CipherResult.Error("Key cannot be empty")
        }

        return try {
            val keyBytes = params.key!!.toByteArray(Charsets.UTF_8)
            val decodedBytes = Base64.decode(
                params.text,
                Base64.NO_WRAP
            )

            val decrypted = decodedBytes.mapIndexed { index, byte ->
                byte xor keyBytes[index % keyBytes.size]
            }.toByteArray()

            CipherResult.Success(String(decrypted, Charsets.UTF_8))
        } catch (e: IllegalArgumentException) {
            CipherResult.Error("Invalid Base64 input")
        } catch (e: Exception) {
            CipherResult.Error("Decryption failed: ${e.message}")
        }
    }
}