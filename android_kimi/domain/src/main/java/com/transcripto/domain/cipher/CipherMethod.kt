package com.transcripto.domain.cipher

interface CipherMethod {
    val name: String
    
    fun encrypt(input: String, params: CipherParams): String
    
    fun decrypt(input: String, params: CipherParams): String
}
