package com.transcripto.crypto

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Base64CodecTest {
    private val codec = Base64Codec()

    @Test
    fun `encode and decode without salt`() {
        val input = "Hello World"
        val encoded = codec.encrypt(input, emptyMap())
        val decoded = codec.decrypt(encoded, emptyMap())
        assertThat(decoded).isEqualTo(input)
    }

    @Test
    fun `encode and decode with salt`() {
        val input = "Hello World"
        val params = mapOf("salt" to "salt")
        val encoded = codec.encrypt(input, params)
        val decoded = codec.decrypt(encoded, params)
        assertThat(decoded).isEqualTo(input)
    }
}
