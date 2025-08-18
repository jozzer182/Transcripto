package com.example.transcripto.domain.crypto

sealed class CipherMethod {
    abstract fun encrypt(input: String, params: CipherParams): String
    abstract fun decrypt(input: String, params: CipherParams): String

    data class CipherParams(
        val key: String = "",
        val shift: Int = 0,
        val salt: String = ""
    )

    object Caesar : CipherMethod() {
        override fun encrypt(input: String, params: CipherParams): String {
            throw NotImplementedError()
        }

        override fun decrypt(input: String, params: CipherParams): String {
            throw NotImplementedError()
        }
    }

    object Base64 : CipherMethod() {
        override fun encrypt(input: String, params: CipherParams): String {
            throw NotImplementedError()
        }

        override fun decrypt(input: String, params: CipherParams): String {
            throw NotImplementedError()
        }
    }

    object Vigenere : CipherMethod() {
        override fun encrypt(input: String, params: CipherParams): String {
            throw NotImplementedError()
        }

        override fun decrypt(input: String, params: CipherParams): String {
            throw NotImplementedError()
        }
    }

    object Xor : CipherMethod() {
        override fun encrypt(input: String, params: CipherParams): String {
            throw NotImplementedError()
        }

        override fun decrypt(input: String, params: CipherParams): String {
            throw NotImplementedError()
        }
    }
}