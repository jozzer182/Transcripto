package com.zarabandajose.transcripto.domain.usecase

import com.zarabandajose.transcripto.core.common.AppResult
import com.zarabandajose.transcripto.core.common.runCatchingResult
import com.zarabandajose.transcripto.domain.cipher.CipherMethod
import com.zarabandajose.transcripto.domain.model.Method

class DecryptText(
    private val cipherMap: Map<Method, CipherMethod>,
) {
    operator fun invoke(
        method: Method,
        input: String,
        params: CipherMethod.Params,
    ): AppResult<String> = runCatchingResult {
        val cipher = cipherMap[method] ?: error("Método no disponible: $method")
        cipher.decrypt(input, params)
    }
}
