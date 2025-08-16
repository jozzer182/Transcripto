package com.transcripto.crypto

import com.transcripto.domain.CipherMethod
import com.transcripto.domain.CipherRepository
import com.transcripto.domain.Method

class CipherRepositoryImpl : CipherRepository {
    private val ciphers = mapOf(
        Method.CAESAR to CaesarCipher(),
        Method.BASE64 to Base64Codec(),
        Method.VIGENERE to VigenereCipher(),
        Method.XOR to XorCipher()
    )

    override fun getCipher(method: Method): CipherMethod = ciphers[method] ?: throw IllegalArgumentException("Método no soportado")
}
