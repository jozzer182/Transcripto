package com.transcripto.data.crypto.cipher

import com.transcripto.domain.model.CipherParams
import com.transcripto.domain.model.CipherResult
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class Base64CodecTest {

    private lateinit var base64Codec: Base64Codec

    @Before
    fun setup() {
        base64Codec = Base64Codec()
    }

    @Test
    fun `encode with valid input returns success`() {
        val params = CipherParams("Hello World", "")
        val result = base64Codec.encrypt(params)

        assertTrue(result is CipherResult.Success)
        assertEquals("SGVsbG8gV29ybGQ=", (result as CipherResult.Success).data)
    }

    @Test
    fun `decode with valid input returns success`() {
        val params = CipherParams("SGVsbG8gV29ybGQ=", "")
        val result = base64Codec.decrypt(params)

        assertTrue(result is CipherResult.Success)
        assertEquals("Hello World", (result as CipherResult.Success).data)
    }

    @Test
    fun `encode with empty text returns error`() {
        val params = CipherParams("", "")
        val result = base64Codec.encrypt(params)

        assertTrue(result is CipherResult.Error)
    }

    @Test
    fun `decode with invalid base64 returns error`() {
        val params = CipherParams("invalid-base64!", "")
        val result = base64Codec.decrypt(params)

        assertTrue(result is CipherResult.Error)
    }

    @Test
    fun `getName returns correct name`() {
        assertEquals("BASE64", base64Codec.getName())
    }

    @Test
    fun `encode handles special characters`() {
        val params = CipherParams("Hello, 世界! 🌍", "")
        val result = base64Codec.encrypt(params)

        assertTrue(result is CipherResult.Success)
        assertNotNull((result as CipherResult.Success).data)
    }

    @Test
    fun `round trip encoding and decoding works`() {
        val originalText = "Test message with numbers 123 and symbols !@#"
        
        val encodeResult = base64Codec.encrypt(CipherParams(originalText, ""))
        assertTrue(encodeResult is CipherResult.Success)
        
        val encodedText = (encodeResult as CipherResult.Success).data
        val decodeResult = base64Codec.decrypt(CipherParams(encodedText, ""))
        assertTrue(decodeResult is CipherResult.Success)
        
        assertEquals(originalText, (decodeResult as CipherResult.Success).data)
    }
}