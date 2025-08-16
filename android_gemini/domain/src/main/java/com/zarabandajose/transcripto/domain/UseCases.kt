package com.zarabandajose.transcripto.domain

import javax.inject.Inject

class EncryptText @Inject constructor(private val cipherMethod: CipherMethod) {
    operator fun invoke(input: String, params: EncryptionParams): String {
        return cipherMethod.encrypt(input, params)
    }
}

class DecryptText @Inject constructor(private val cipherMethod: CipherMethod) {
    operator fun invoke(input: String, params: DecryptionParams): String {
        return cipherMethod.decrypt(input, params)
    }
}
