package com.transcripto.data.crypto.repository

import com.transcripto.data.crypto.cipher.*
import com.transcripto.domain.model.CipherMethod
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CipherRepositoryImplTest {

    private lateinit var cipherRepository: CipherRepositoryImpl

    @Before
    fun setup() {
        val cipherMethods = mapOf<String, CipherMethod>(
            "CAESAR" to CaesarCipher(),
            "BASE64" to Base64Codec(),
            "VIGENERE" to VigenereCipher(),
            "XOR" to XorCipher()
        )
        cipherRepository = CipherRepositoryImpl(cipherMethods)
    }

    @Test
    fun `getCipherMethods returns all available ciphers`() {
        val ciphers = cipherRepository.getCipherMethods()
        
        assertEquals(4, ciphers.size)
        assertTrue(ciphers.containsKey("CAESAR"))
        assertTrue(ciphers.containsKey("BASE64"))
        assertTrue(ciphers.containsKey("VIGENERE"))
        assertTrue(ciphers.containsKey("XOR"))
    }

    @Test
    fun `getAvailableMethods returns correct list`() {
        val methods = cipherRepository.getAvailableMethods()
        
        assertEquals(4, methods.size)
        assertTrue(methods.contains("CAESAR"))
        assertTrue(methods.contains("BASE64"))
        assertTrue(methods.contains("VIGENERE"))
        assertTrue(methods.contains("XOR"))
    }

    @Test
    fun `getCipher returns correct cipher for method`() {
        val caesar = cipherRepository.getCipher("CAESAR")
        val base64 = cipherRepository.getCipher("BASE64")
        
        assertNotNull(caesar)
        assertNotNull(base64)
        assertTrue(caesar is CaesarCipher)
        assertTrue(base64 is Base64Codec)
    }

    @Test
    fun `getCipher returns null for unknown method`() {
        val cipher = cipherRepository.getCipher("UNKNOWN")
        
        assertNull(cipher)
    }
}