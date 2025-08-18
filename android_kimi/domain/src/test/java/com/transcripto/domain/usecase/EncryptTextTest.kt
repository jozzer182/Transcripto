package com.transcripto.domain.usecase

import com.transcripto.domain.model.CipherMethod
import com.transcripto.domain.model.CipherResult
import com.transcripto.domain.repository.CipherRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class EncryptTextTest {

    private lateinit var encryptText: EncryptText
    private lateinit var mockCipherRepository: CipherRepository
    private lateinit var mockCaesarCipher: CipherMethod
    private lateinit var mockBase64Cipher: CipherMethod

    @Before
    fun setup() {
        mockCaesarCipher = mock()
        mockBase64Cipher = mock()
        mockCipherRepository = mock()
        
        val cipherMethods = mapOf(
            "CAESAR" to mockCaesarCipher,
            "BASE64" to mockBase64Cipher
        )
        
        whenever(mockCipherRepository.getCipherMethods()).thenReturn(cipherMethods)
        encryptText = EncryptText(mockCipherRepository)
    }

    @Test
    fun `execute with valid method returns success`() {
        val text = "HELLO"
        val key = "3"
        val salt = "abc123"
        val encryptedText = "KHOOR"

        whenever(mockCaesarCipher.encrypt(any())).thenReturn(CipherResult.Success(encryptedText))

        val result = encryptText.execute("CAESAR", text, key, salt)

        assertTrue(result is CipherResult.Success)
        assertEquals(encryptedText, (result as CipherResult.Success).data)
        verify(mockCaesarCipher).encrypt(any())
    }

    @Test
    fun `execute with unknown method returns error`() {
        val result = encryptText.execute("UNKNOWN", "HELLO", "3", "abc123")

        assertTrue(result is CipherResult.Error)
        verify(mockCaesarCipher, never()).encrypt(any())
    }

    @Test
    fun `execute with empty text returns error`() {
        val result = encryptText.execute("CAESAR", "", "3", "abc123")

        assertTrue(result is CipherResult.Error)
        verify(mockCaesarCipher, never()).encrypt(any())
    }

    @Test
    fun `execute delegates to correct cipher method`() {
        val text = "HELLO"
        val key = "3"
        val salt = "abc123"

        encryptText.execute("BASE64", text, key, salt)

        verify(mockBase64Cipher).encrypt(any())
        verify(mockCaesarCipher, never()).encrypt(any())
    }

    @Test
    fun `execute propagates cipher error`() {
        val errorMessage = "Invalid key"

        whenever(mockCaesarCipher.encrypt(any())).thenReturn(CipherResult.Error(errorMessage))

        val result = encryptText.execute("CAESAR", "HELLO", "invalid", "abc123")

        assertTrue(result is CipherResult.Error)
        assertEquals(errorMessage, (result as CipherResult.Error).message)
    }
}