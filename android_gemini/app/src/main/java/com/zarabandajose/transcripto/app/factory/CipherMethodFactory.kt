package com.zarabandajose.transcripto.app.factory

import com.zarabandajose.transcripto.data.crypto.Base64Codec
import com.zarabandajose.transcripto.data.crypto.CaesarCipher
import com.zarabandajose.transcripto.data.crypto.VigenereCipher
import com.zarabandajose.transcripto.data.crypto.XorCipher
import com.zarabandajose.transcripto.domain.CipherMethod
import com.zarabandajose.transcripto.domain.CipherMethodType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CipherMethodFactory @Inject constructor(
    private val caesarCipher: CaesarCipher,
    private val base64Codec: Base64Codec,
    private val vigenereCipher: VigenereCipher,
    private val xorCipher: XorCipher
) {
    fun create(method: CipherMethodType): CipherMethod {
        return when (method) {
            CipherMethodType.Caesar -> caesarCipher
            CipherMethodType.Base64 -> base64Codec
            CipherMethodType.Vigenere -> vigenereCipher
            CipherMethodType.XOR -> xorCipher
        }
    }
}
