package com.zarabandajose.transcripto.data.crypto

import com.google.common.truth.Truth.assertThat
import com.zarabandajose.transcripto.domain.DecryptionParams
import com.zarabandajose.transcripto.domain.EncryptionParams
import org.junit.Before
import org.junit.Test

class CaesarCipherTest {

    private lateinit var caesarCipher: CaesarCipher

    @Before
    fun setUp() {
        caesarCipher = CaesarCipher()
    }

    @Test
    fun `encrypt with positive shift`() {
        val input = "hello"
        val params = EncryptionParams(com.zarabandajose.transcripto.domain.CipherMethodType.Caesar, key = "3")
        val result = caesarCipher.encrypt(input, params)
        assertThat(result).isEqualTo("khoor")
    }

    @Test
    fun `decrypt with positive shift`() {
        val input = "khoor"
        val params = DecryptionParams(com.zarabandajose.transcripto.domain.CipherMethodType.Caesar, key = "3")
        val result = caesarCipher.decrypt(input, params)
        assertThat(result).isEqualTo("hello")
    }

    @Test
    fun `encrypt with negative shift`() {
        val input = "hello"
        val params = EncryptionParams(com.zarabandajose.transcripto.domain.CipherMethodType.Caesar, key = "-3")
        val result = caesarCipher.encrypt(input, params)
        assertThat(result).isEqualTo("ebiil")
    }

    @Test
    fun `decrypt with negative shift`() {
        val input = "ebiil"
        val params = DecryptionParams(com.zarabandajose.transcripto.domain.CipherMethodType.Caesar, key = "-3")
        val result = caesarCipher.decrypt(input, params)
        assertThat(result).isEqualTo("hello")
    }

    @Test
    fun `encrypt with wrapping`() {
        val input = "xyz"
        val params = EncryptionParams(com.zarabandajose.transcripto.domain.CipherMethodType.Caesar, key = "3")
        val result = caesarCipher.encrypt(input, params)
        assertThat(result).isEqualTo("abc")
    }

    @Test
    fun `decrypt with wrapping`() {
        val input = "abc"
        val params = DecryptionParams(com.zarabandajose.transcripto.domain.CipherMethodType.Caesar, key = "3")
        val result = caesarCipher.decrypt(input, params)
        assertThat(result).isEqualTo("xyz")
    }

    @Test
    fun `encrypt with non-alphabetic characters`() {
        val input = "hello, world!"
        val params = EncryptionParams(com.zarabandajose.transcripto.domain.CipherMethodType.Caesar, key = "3")
        val result = caesarCipher.encrypt(input, params)
        assertThat(result).isEqualTo("khoor, zruog!")
    }

    @Test
    fun `decrypt with non-alphabetic characters`() {
        val input = "khoor, zruog!"
        val params = DecryptionParams(com.zarabandajose.transcripto.domain.CipherMethodType.Caesar, key = "3")
        val result = caesarCipher.decrypt(input, params)
        assertThat(result).isEqualTo("hello, world!")
    }
}
