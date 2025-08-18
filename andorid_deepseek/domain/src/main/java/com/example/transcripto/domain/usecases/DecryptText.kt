package com.example.transcripto.domain.usecases

import com.example.transcripto.domain.crypto.CipherMethod
import com.example.transcripto.domain.crypto.CipherMethod.CipherParams
import javax.inject.Inject

class DecryptText @Inject constructor() {
    operator fun invoke(
        input: String,
        method: CipherMethod,
        params: CipherParams
    ): String {
        return method.decrypt(input, params)
    }
}