package com.transcripto.data.crypto.parser

import com.transcripto.domain.model.CipherMethod
import com.transcripto.domain.model.EnvelopeData
import com.transcripto.domain.repository.EnvelopeParser
import javax.inject.Inject

/**
 * Implementation of EnvelopeParser for parsing envelope format strings.
 * Format: method=<METHOD>;salt=<BASE64_OR_EMPTY>;payload=<RESULT>
 */
class EnvelopeParserImpl @Inject constructor() : EnvelopeParser {

    companion object {
        private const val ENVELOPE_PREFIX = "method="
        private const val SALT_PREFIX = "salt="
        private const val PAYLOAD_PREFIX = "payload="
    }

    override fun parseEnvelope(envelope: String): EnvelopeData? {
        if (!isEnvelopeFormat(envelope)) return null

        return try {
            val parts = envelope.split(';')
            
            var method: CipherMethod? = null
            var salt: String? = null
            var payload: String? = null

            for (part in parts) {
                when {
                    part.startsWith(ENVELOPE_PREFIX) -> {
                        val methodName = part.substring(ENVELOPE_PREFIX.length)
                        method = try {
                            CipherMethod.valueOf(methodName.uppercase())
                        } catch (e: IllegalArgumentException) {
                            return null
                        }
                    }
                    part.startsWith(SALT_PREFIX) -> {
                        salt = part.substring(SALT_PREFIX.length).takeIf { it.isNotEmpty() }
                    }
                    part.startsWith(PAYLOAD_PREFIX) -> {
                        payload = part.substring(PAYLOAD_PREFIX.length)
                    }
                }
            }

            if (method != null && payload != null) {
                EnvelopeData(method, salt, payload)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override fun createEnvelope(method: String, salt: String?, payload: String): String {
        return buildString {
            append("method=$method")
            if (salt != null) {
                append(";salt=$salt")
            } else {
                append(";salt=")
            }
            append(";payload=$payload")
        }
    }

    override fun isEnvelopeFormat(input: String): Boolean {
        return input.startsWith(ENVELOPE_PREFIX) && input.contains(";")
    }
}