package com.zarabandajose.transcripto.domain.usecase

import com.zarabandajose.transcripto.core.common.AppResult
import com.zarabandajose.transcripto.core.common.runCatchingResult
import com.zarabandajose.transcripto.domain.cipher.CipherMethod
import com.zarabandajose.transcripto.domain.model.Envelope
import com.zarabandajose.transcripto.domain.model.Method
import java.util.Base64

class EncryptText(
    private val cipherMap: Map<Method, CipherMethod>,
) {
    operator fun invoke(
        method: Method,
        input: String,
        params: CipherMethod.Params,
        includeEnvelope: Boolean = true,
    ): AppResult<String> = runCatchingResult {
        val cipher = cipherMap[method] ?: error("Método no disponible: $method")
        val saltB64 = if (params.useSalt) params.salt?.let { Base64.getEncoder().encodeToString(it) } else ""
        val payload = cipher.encrypt(input, params)
        if (includeEnvelope) {
            "method=${method.name};salt=${saltB64};payload=${payload}"
        } else payload
    }
}
