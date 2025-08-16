package com.zarabandajose.transcripto.domain.model

data class Envelope(
    val method: Method,
    val salt: ByteArray?,
    val payload: String,
)

enum class Method { BASE64, CAESAR, VIGENERE, XOR }
