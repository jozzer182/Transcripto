package com.transcripto.domain
import kotlin.collections.Map

interface DetectAndParseEnvelopeUseCase {
    operator fun invoke(input: String): Map<String, Any>?
}
