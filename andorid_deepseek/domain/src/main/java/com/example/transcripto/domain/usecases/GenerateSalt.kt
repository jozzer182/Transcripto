package com.example.transcripto.domain.usecases

import com.example.transcripto.domain.crypto.CipherMethod.CipherParams
import javax.inject.Inject

class GenerateSalt @Inject constructor() {
    operator fun invoke(length: Int = 12): String {
        require(length in 8..16) { "Salt length must be between 8 and 16 characters" }
        return ""
    }
}