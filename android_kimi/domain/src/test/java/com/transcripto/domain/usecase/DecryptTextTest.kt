package com.transcripto.domain.usecase

import com.transcripto.domain.model.CipherMethod
import com.transcripto.domain.model.CipherResult
import com.transcripto.domain.repository.CipherRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class DecryptTextTest {

    private lateinit var decryptText: DecryptText
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
        decryptText = DecryptText(mockCipherRepository)
    }

    @Test
    fun `execute with valid method returns success`() {
        val encryptedText = "KHOOR"
        val key = "3"
        val salt = "abc123"
        val decryptedText = "HELLO"

        whenever(mockCaesarCipher.decrypt(any())).thenReturn(CipherResult.Success(decryptedText))

        val result = decryptText.execute("CAESAR", encryptedText, key, salt)

        assertTrue(result is CipherResult.Success)
        assertEquals(decryptedText, (result as CipherResult.Success).data)
        verify(mockCaesarCipher).decrypt(any())
    }

    @Test
    fun `execute with unknown method returns error`() {
        val result = decryptText.execute("UNKNOWN", "KHOOR", "3", "abc123")

        assertTrue(result is CipherResult.Error)
        verify(mockCaesarCipher, never()).decrypt(any())
    }

    @Test
    fun `execute with empty encrypted text returns error`() {
        val result = decryptText.execute("CAESAR", "", "3", "abc123")

        assertTrue(result is CipherResult.Error)
        verify(mockCaesarCipher, never()).decrypt(any())
    }

    @Test
    fun `execute delegates to correct cipher method`() {
        val encryptedText = "SGVsbG8="
        val key = ""
        val salt = ""

        decryptText.execute("BASE64", encryptedText, key, salt)

        verify(mockBase64Cipher).decrypt(any())
        verify(mockCaesarCipher, never()).decrypt(any())
    }

    @Test
    fun `execute propagates cipher error`() {
        val errorMessage = "Invalid encrypted text"

        whenever(mockCaesarCipher.decrypt(any())).thenReturn(CipherResult.Error(errorMessage))

        val result = decryptText.execute("CAESAR", "invalid", "3", "abc123")

        assertTrue(result is CipherResult.Error)
        assertEquals(errorMessage, (result as CipherResult.Error).message)
    }

    @Test
    fun `execute with salt parameter passes salt to cipher`() {
        val encryptedText = "encrypted"
        val key = "key"
        val salt = "salt123"

        whenever(mockCaesarCipher.decrypt(any())).thenReturn(CipherResult.Success("decrypted"))

        decryptText.execute("CAESAR", encryptedText, key, salt)

        verify(mockCaesarCipher).decrypt(any())
    }
}