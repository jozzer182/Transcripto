package com.zarabandajose.transcripto.domain.usecase

import com.zarabandajose.transcripto.core.common.AppResult
import com.zarabandajose.transcripto.core.common.runCatchingResult
import com.zarabandajose.transcripto.domain.model.Envelope
import com.zarabandajose.transcripto.domain.model.Method
import java.util.Base64

class DetectAndParseEnvelope {
    private val regex = Regex("^method=([A-Z]+);salt=([^;]*);payload=(.*)$", RegexOption.DOT_MATCHES_ALL)

    operator fun invoke(text: String): AppResult<Envelope?> = runCatchingResult {
        val m = regex.find(text) ?: return@runCatchingResult null
        val method = Method.valueOf(m.groupValues[1])
        val salt = m.groupValues[2].takeIf { it.isNotEmpty() }?.let { Base64.getDecoder().decode(it) }
        val payload = m.groupValues[3]
        Envelope(method, salt, payload)
    }
}
