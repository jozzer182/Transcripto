package com.zarabandajose.transcripto.data.crypto.repository

import com.zarabandajose.transcripto.core.common.Result
import com.zarabandajose.transcripto.data.crypto.cipher.Base64Codec
import com.zarabandajose.transcripto.data.crypto.cipher.CaesarCipher
import com.zarabandajose.transcripto.data.crypto.cipher.VigenereCipher
import com.zarabandajose.transcripto.data.crypto.cipher.XorCipher
import com.zarabandajose.transcripto.domain.model.CipherMethod
import com.zarabandajose.transcripto.domain.model.CipherParams
import com.zarabandajose.transcripto.domain.repository.CipherRepository
import javax.inject.Inject

/**
 * Implementación del repositorio de cifrado.
 * Delega las operaciones a los diferentes cifradores según el método seleccionado.
 */
class CipherRepositoryImpl @Inject constructor(
    private val caesarCipher: CaesarCipher,
    private val base64Codec: Base64Codec,
    private val vigenereCipher: VigenereCipher,
    private val xorCipher: XorCipher
) : CipherRepository {
    
    override suspend fun encrypt(input: String, params: CipherParams): Result<String> {
        return when (params.method) {
            CipherMethod.CAESAR -> caesarCipher.encrypt(input, params)
            CipherMethod.BASE64 -> base64Codec.encrypt(input, params)
            CipherMethod.VIGENERE -> vigenereCipher.encrypt(input, params)
            CipherMethod.XOR -> xorCipher.encrypt(input, params)
        }
    }
    
    override suspend fun decrypt(input: String, params: CipherParams): Result<String> {
        return when (params.method) {
            CipherMethod.CAESAR -> caesarCipher.decrypt(input, params)
            CipherMethod.BASE64 -> base64Codec.decrypt(input, params)
            CipherMethod.VIGENERE -> vigenereCipher.decrypt(input, params)
            CipherMethod.XOR -> xorCipher.decrypt(input, params)
        }
    }
}
