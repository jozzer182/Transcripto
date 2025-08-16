package com.transcripto.domain

interface CipherMethod {
    fun encrypt(input: String, params: Map<String, Any>): String
    fun decrypt(input: String, params: Map<String, Any>): String
}
