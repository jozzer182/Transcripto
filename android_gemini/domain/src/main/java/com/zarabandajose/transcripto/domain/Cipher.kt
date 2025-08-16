package com.zarabandajose.transcripto.domain

sealed class CipherMethodType {
    object Caesar : CipherMethodType()
    object Base64 : CipherMethodType()
    object Vigenere : CipherMethodType()
    object XOR : CipherMethodType()
}

data class EncryptionParams(
    val method: CipherMethodType,
    val key: String? = null,
    val salt: String? = null
)

data class DecryptionParams(
    val method: CipherMethodType,
    val key: String? = null,
    val salt: String? = null
)

interface CipherMethod {
    fun encrypt(input: String, params: EncryptionParams): String
    fun decrypt(input: String, params: DecryptionParams): String
}
