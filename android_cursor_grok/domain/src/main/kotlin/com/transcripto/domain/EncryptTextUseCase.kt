package com.transcripto.domain

interface EncryptTextUseCase {
    operator fun invoke(input: String, method: Method, key: String?, shift: Int?, salt: String?): String
}
