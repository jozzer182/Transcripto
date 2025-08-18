package com.transcripto.domain.cipher

data class CipherParams(
    val key: String? = null,
    val shift: Int? = null,
    val salt: String? = null,
    val useSalt: Boolean = false
)
