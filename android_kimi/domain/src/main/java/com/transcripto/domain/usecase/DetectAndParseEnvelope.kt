package com.transcripto.domain.usecase

import com.transcripto.domain.model.EnvelopeData
import com.transcripto.domain.repository.EnvelopeParser
import javax.inject.Inject

/**
 * Use case for detecting and parsing envelope format strings.
 */
class DetectAndParseEnvelope @Inject constructor(
    private val envelopeParser: EnvelopeParser
) {
    /**
     * Detects if the input is in envelope format and parses it.
     * @param input The string to check and parse
     * @return EnvelopeData if input is in envelope format, null otherwise
     */
    operator fun invoke(input: String): EnvelopeData? {
        if (input.isBlank()) return null
        return envelopeParser.parseEnvelope(input)
    }

    /**
     * Checks if the input appears to be in envelope format.
     * @param input The string to check
     * @return true if the input matches envelope format
     */
    fun isEnvelopeFormat(input: String): Boolean {
        return envelopeParser.isEnvelopeFormat(input)
    }
}