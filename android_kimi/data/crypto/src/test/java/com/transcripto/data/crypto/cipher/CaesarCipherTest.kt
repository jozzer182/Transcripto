package com.transcripto.data.crypto.cipher

import com.transcripto.domain.model.CipherParams
import com.transcripto.domain.model.CipherResult
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CaesarCipherTest {

    private lateinit var caesarCipher: CaesarCipher

    @Before
    fun setup() {
        caesarCipher = CaesarCipher()
    }

    @Test
    fun `encrypt with valid input returns success`() {
        val params = CipherParams("HELLO", "3")
        val result = caesarCipher.encrypt(params)

        assertTrue(result is CipherResult.Success)
        assertEquals("KHOOR", (result as CipherResult.Success).data)
    }

    @Test
    fun `decrypt with valid input returns success`() {
        val params = CipherParams("KHOOR", "3")
        val result = caesarCipher.decrypt(params)

        assertTrue(result is CipherResult.Success)
        assertEquals("HELLO", (result as CipherResult.Success).data)
    }

    @Test
    fun `encrypt with empty text returns error`() {
        val params = CipherParams("", "3")
        val result = caesarCipher.encrypt(params)

        assertTrue(result is CipherResult.Error)
    }

    @Test
    fun `encrypt with blank text returns error`() {
        val params = CipherParams("   ", "3")
        val result = caesarCipher.encrypt(params)

        assertTrue(result is CipherResult.Error)
    }

    @Test
    fun `encrypt with invalid key returns error`() {
        val params = CipherParams("HELLO", "invalid")
        val result = caesarCipher.encrypt(params)

        assertTrue(result is CipherResult.Error)
    }

    @Test
    fun `encrypt handles lowercase letters`() {
        val params = CipherParams("hello", "3")
        val result = caesarCipher.encrypt(params)

        assertTrue(result is CipherResult.Success)
        assertEquals("KHOOR", (result as CipherResult.Success).data)
    }

    @Test
    fun `encrypt handles special characters`() {
        val params = CipherParams("HELLO, WORLD!", "3")
        val result = caesarCipher.encrypt(params)

        assertTrue(result is CipherResult.Success)
        assertEquals("KHOOR, ZRUOG!", (result as CipherResult.Success).data)
    }

    @Test
    fun `getName returns correct name`() {
        assertEquals("CAESAR", caesarCipher.getName())
    }
}