package com.transcripto.crypto

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class CaesarCipherTest {
    private val cipher = CaesarCipher()

    @Test
    fun `encrypt and decrypt without salt`() {
        val input = "Hello World"
        val params = mapOf("shift" to 3)
        val encrypted = cipher.encrypt(input, params)
        val decrypted = cipher.decrypt(encrypted, params)
        assertThat(decrypted).isEqualTo(input)
    }

    @Test
    fun `encrypt and decrypt with salt`() {
        val input = "Hello World"
        val params = mapOf("shift" to 3, "salt" to "salt")
        val encrypted = cipher.encrypt(input, params)
        val decrypted = cipher.decrypt(encrypted, params)
        assertThat(decrypted).isEqualTo(input)
    }

    @Test
    fun `handles non-letter characters`() {
        val input = "Hello, World! 123"
        val params = mapOf("shift" to 5)
        val encrypted = cipher.encrypt(input, params)
        assertThat(encrypted).contains("123")
        assertThat(encrypted).contains(", ")
        assertThat(encrypted).contains("!")
    }
}
