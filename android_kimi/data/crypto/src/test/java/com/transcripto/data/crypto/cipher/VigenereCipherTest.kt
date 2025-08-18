package com.transcripto.data.crypto.cipher

import com.transcripto.domain.model.CipherParams
import com.transcripto.domain.model.CipherResult
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class VigenereCipherTest {

    private lateinit var vigenereCipher: VigenereCipher

    @Before
    fun setup() {
        vigenereCipher = VigenereCipher()
    }

    @Test
    fun `encrypt with valid input returns success`() {
        val params = CipherParams("HELLO WORLD", "KEY")
        val result = vigenereCipher.encrypt(params)

        assertTrue(result is CipherResult.Success)
        assertEquals("RIJVS UYVJN", (result as CipherResult.Success).data)
    }

    @Test
    fun `decrypt with valid input returns success`() {
        val params = CipherParams("RIJVS UYVJN", "KEY")
        val result = vigenereCipher.decrypt(params)

        assertTrue(result is CipherResult.Success)
        assertEquals("HELLO WORLD", (result as CipherResult.Success).data)
    }

    @Test
    fun `encrypt with empty text returns error`() {
        val params = CipherParams("", "KEY")
        val result = vigenereCipher.encrypt(params)

        assertTrue(result is CipherResult.Error)
    }

    @Test
    fun `encrypt with blank text returns error`() {
        val params = CipherParams("   ", "KEY")
        val result = vigenereCipher.encrypt(params)

        assertTrue(result is CipherResult.Error)
    }

    @Test
    fun `encrypt with empty key returns error`() {
        val params = CipherParams("HELLO", "")
        val result = vigenereCipher.encrypt(params)

        assertTrue(result is CipherResult.Error)
    }

    @Test
    fun `encrypt with blank key returns error`() {
        val params = CipherParams("HELLO", "   ")
        val result = vigenereCipher.encrypt(params)

        assertTrue(result is CipherResult.Error)
    }

    @Test
    fun `encrypt handles lowercase letters`() {
        val params = CipherParams("hello world", "key")
        val result = vigenereCipher.encrypt(params)

        assertTrue(result is CipherResult.Success)
        assertEquals("RIJVS UYVJN", (result as CipherResult.Success).data)
    }

    @Test
    fun `encrypt handles numbers and special characters`() {
        val params = CipherParams("HELLO123!@#", "KEY")
        val result = vigenereCipher.encrypt(params)

        assertTrue(result is CipherResult.Success)
        assertEquals("RIJVS123!@#", (result as CipherResult.Success).data)
    }

    @Test
    fun `round trip encryption and decryption works`() {
        val originalText = "The quick brown fox jumps over the lazy dog"
        val key = "SECRET"
        
        val encryptResult = vigenereCipher.encrypt(CipherParams(originalText, key))
        assertTrue(encryptResult is CipherResult.Success)
        
        val encryptedText = (encryptResult as CipherResult.Success).data
        val decryptResult = vigenereCipher.decrypt(CipherParams(encryptedText, key))
        assertTrue(decryptResult is CipherResult.Success)
        
        assertEquals(originalText.toUpperCase(), (decryptResult as CipherResult.Success).data)
    }

    @Test
    fun `getName returns correct name`() {
        assertEquals("VIGENERE", vigenereCipher.getName())
    }
}