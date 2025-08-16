package com.transcripto.crypto

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class VigenereCipherTest {
    private val cipher = VigenereCipher()

    @Test
    fun `encrypt and decrypt with key`() {
        val input = "Hello World"
        val params = mapOf("key" to "key")
        val encrypted = cipher.encrypt(input, params)
        val decrypted = cipher.decrypt(encrypted, params)
        assertThat(decrypted).isEqualTo(input)
    }

    @Test
    fun `invalid key throws exception`() {
        assertThrows<IllegalArgumentException> {
            cipher.encrypt("text", mapOf("key" to "123"))
        }
    }
}
