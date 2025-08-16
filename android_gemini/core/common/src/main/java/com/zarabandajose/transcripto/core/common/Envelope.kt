package com.zarabandajose.transcripto.core.common

import android.util.Base64

object Envelope {
    fun pack(method: String, salt: String?, payload: String): String {
        val saltBase64 = salt?.let { Base64.encodeToString(it.toByteArray(), Base64.NO_WRAP) } ?: ""
        return "method=$method;salt=$saltBase64;payload=$payload"
    }

    fun unpack(envelope: String): Triple<String, String?, String>? {
        if (!envelope.startsWith("method=")) return null
        val parts = envelope.split(";")
        if (parts.size != 3) return null
        val method = parts[0].substringAfter("=")
        val saltBase64 = parts[1].substringAfter("=")
        val payload = parts[2].substringAfter("=")
        val salt = if (saltBase64.isNotEmpty()) {
            String(Base64.decode(saltBase64, Base64.NO_WRAP))
        } else {
            null
        }
        return Triple(method, salt, payload)
    }
}
