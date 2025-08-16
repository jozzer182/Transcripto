package com.zarabandajose.transcripto.domain.cipher

import com.zarabandajose.transcripto.domain.model.Method

/**
 * Define un método de cifrado genérico.
 */
interface CipherMethod {
    val method: Method
    fun encrypt(input: String, params: Params): String
    fun decrypt(input: String, params: Params): String

    data class Params(
        val key: String? = null,
        val shift: Int? = null,
        val salt: ByteArray? = null,
        val useSalt: Boolean = false,
    )
}
