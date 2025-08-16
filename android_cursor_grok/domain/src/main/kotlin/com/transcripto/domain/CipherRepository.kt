package com.transcripto.domain

interface CipherRepository {
    fun getCipher(method: Method): CipherMethod
}
