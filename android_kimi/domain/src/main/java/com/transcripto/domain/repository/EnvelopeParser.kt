package com.transcripto.domain.repository

import com.transcripto.domain.model.EnvelopeData

/**
 * Interface for parsing envelope format strings.
 */
interface EnvelopeParser {
    /**
     * Parses an envelope string into its components.
     * @param envelope The envelope string to parse
     * @return EnvelopeData if parsing is successful, null otherwise
     */
    fun parseEnvelope(envelope: String): EnvelopeData?

    /**
     * Creates an envelope string from the given components.
     * @param method The cipher method used
     * @param salt The salt used (optional)
     * @param payload The encrypted payload
     * @return The formatted envelope string
     */
    fun createEnvelope(
        method: String,
        salt: String? = null,
        payload: String
    ): String

    /**
     * Checks if a string appears to be an envelope format.
     * @param input The string to check
     * @return true if the string matches envelope format
     */
    fun isEnvelopeFormat(input: String): Boolean
}