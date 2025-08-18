package com.transcripto.data.crypto.cipher

import com.transcripto.domain.model.CipherParams
import com.transcripto.domain.model.CipherResult
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class XorCipherTest {

    private lateinit var xorCipher: XorCipher

    @Before
    fun setup() {
        xorCipher = XorCipher()
    }

    @Test
    fun `encrypt with valid input returns success`() {
        val params = CipherParams("HELLO", "KEY")
        val result = xorCipher.encrypt(params)

        assertTrue(result is CipherResult.Success)
        assertNotNull((result as CipherResult.Success).data)
    }

    @Test
    fun `decrypt with valid input returns success`() {
        val params = CipherParams("HELLO", "KEY")
        val encryptResult = xorCipher.encrypt(params)
        assertTrue(encryptResult is CipherResult.Success)
        
        val encryptedText = (encryptResult as CipherResult.Success).data
        val decryptResult = xorCipher.decrypt(CipherParams(encryptedText, "KEY"))
        
        assertTrue(decryptResult is CipherResult.Success)
        assertEquals("HELLO", (decryptResult as CipherResult.Success).data)
    }

    @Test
    fun `encrypt with empty text returns error`() {
        val params = CipherParams("", "KEY")
        val result = xorCipher.encrypt(params)

        assertTrue(result is CipherResult.Error)
    }

    @Test
    fun `encrypt with blank text returns error`() {
        val params = CipherParams("   ", "KEY")
        val result = xorCipher.encrypt(params)

        assertTrue(result is CipherResult.Error)
    }

    @Test
    fun `encrypt with empty key returns error`() {
        val params = CipherParams("HELLO", "")
        val result = xorCipher.encrypt(params)

        assertTrue(result is CipherResult.Error)
    }

    @Test
    fun `encrypt with blank key returns error`() {
        val params = CipherParams("HELLO", "   ")
        val result = xorCipher.encrypt(params)

        assertTrue(result is CipherResult.Error)
    }

    @Test
    fun `round trip encryption and decryption works`() {
        val originalText = "The quick brown fox"
        val key = "SECRET"
        
        val encryptResult = xorCipher.encrypt(CipherParams(originalText, key))
        assertTrue(encryptResult is CipherResult.Success)
        
        val encryptedText = (encryptResult as CipherResult.Success).data
        val decryptResult = xorCipher.decrypt(CipherParams(encryptedText, key))
        assertTrue(decryptResult is CipherResult.Success)
        
        assertEquals(originalText, (decryptResult as CipherResult.Success).data)
    }

    @Test
    fun `encrypt handles special characters`() {
        val params = CipherParams("Hello, 世界! 🌍", "KEY")
        val result = xorCipher.encrypt(params)

        assertTrue(result is CipherResult.Success)
        assertNotNull((result as CipherResult.Success).data)
    }

    @Test
    fun `encrypt with salt returns different results`() {
        val params1 = CipherParams("HELLO", "KEY")
        val params2 = CipherParams("HELLO", "KEY")
        
        val result1 = xorCipher.encrypt(params1)
        val result2 = xorCipher.encrypt(params2)
        
        assertTrue(result1 is CipherResult.Success)
        assertTrue(result2 is CipherResult.Success)
        assertEquals((result1 as CipherResult.Success).data, (result2 as CipherResult.Success).data)
    }

    @Test
    fun `getName returns correct name`() {
        assertEquals("XOR", xorCipher.getName())
    }
}