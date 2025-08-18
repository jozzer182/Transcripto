package com.transcripto.transcripto.integration

import com.transcripto.data.crypto.cipher.*
import com.transcripto.data.crypto.parser.EnvelopeParserImpl
import com.transcripto.data.crypto.provider.SecureRandomSaltProvider
import com.transcripto.data.crypto.repository.CipherRepositoryImpl
import com.transcripto.domain.usecase.*
import org.junit.Assert.*
import org.junit.Test

class TranscriptoIntegrationTest {

    @Test
    fun `full encryption and decryption flow works correctly`() {
        // Setup all components
        val cipherMethods = mapOf(
            "CAESAR" to CaesarCipher(),
            "BASE64" to Base64Codec(),
            "VIGENERE" to VigenereCipher(),
            "XOR" to XorCipher()
        )
        
        val cipherRepository = CipherRepositoryImpl(cipherMethods)
        val envelopeParser = EnvelopeParserImpl()
        val saltProvider = SecureRandomSaltProvider()
        
        val encryptText = EncryptText(cipherRepository)
        val decryptText = DecryptText(cipherRepository)
        val detectAndParseEnvelope = DetectAndParseEnvelope(envelopeParser)
        val generateSalt = GenerateSalt(saltProvider)

        // Test data
        val originalText = "Hello World"
        val key = "3"
        val method = "CAESAR"

        // Generate salt
        val salt = generateSalt.execute()
        assertNotNull(salt)
        assertTrue(salt.isNotEmpty())

        // Encrypt
        val encryptResult = encryptText.execute(method, originalText, key, salt)
        assertTrue(encryptResult is com.transcripto.domain.model.CipherResult.Success)
        val encryptedText = (encryptResult as com.transcripto.domain.model.CipherResult.Success).data
        assertNotEquals(originalText, encryptedText)

        // Create envelope
        val envelopeData = com.transcripto.domain.model.EnvelopeData(method, salt, encryptedText)
        val envelope = envelopeParser.create(envelopeData)
        assertTrue(envelope.contains("method=$method"))
        assertTrue(envelope.contains("salt=$salt"))
        assertTrue(envelope.contains("payload=$encryptedText"))

        // Detect and parse envelope
        val detectResult = detectAndParseEnvelope.execute(envelope)
        assertTrue(detectResult is com.transcripto.domain.usecase.EnvelopeResult.Success)
        val parsedData = (detectResult as com.transcripto.domain.usecase.EnvelopeResult.Success).data
        assertEquals(method, parsedData.method)
        assertEquals(salt, parsedData.salt)
        assertEquals(encryptedText, parsedData.payload)

        // Decrypt
        val decryptResult = decryptText.execute(
            parsedData.method, 
            parsedData.payload, 
            key, 
            parsedData.salt
        )
        assertTrue(decryptResult is com.transcripto.domain.model.CipherResult.Success)
        val decryptedText = (decryptResult as com.transcripto.domain.model.CipherResult.Success).data
        assertEquals(originalText.toUpperCase(), decryptedText)
    }

    @Test
    fun `all cipher methods work in integration`() {
        val cipherMethods = mapOf(
            "CAESAR" to CaesarCipher(),
            "BASE64" to Base64Codec(),
            "VIGENERE" to VigenereCipher(),
            "XOR" to XorCipher()
        )
        
        val cipherRepository = CipherRepositoryImpl(cipherMethods)
        val encryptText = EncryptText(cipherRepository)
        val decryptText = DecryptText(cipherRepository)
        val generateSalt = GenerateSalt(SecureRandomSaltProvider())

        val testData = listOf(
            Triple("CAESAR", "HELLO", "3"),
            Triple("BASE64", "Hello World", ""),
            Triple("VIGENERE", "HELLO", "KEY"),
            Triple("XOR", "Hello", "SECRET")
        )

        testData.forEach { (method, text, key) ->
            val salt = generateSalt.execute()
            
            // Encrypt
            val encryptResult = encryptText.execute(method, text, key, salt)
            assertTrue("$method encryption failed", encryptResult is com.transcripto.domain.model.CipherResult.Success)
            val encrypted = (encryptResult as com.transcripto.domain.model.CipherResult.Success).data
            
            // Decrypt
            val decryptResult = decryptText.execute(method, encrypted, key, salt)
            assertTrue("$method decryption failed", decryptResult is com.transcripto.domain.model.CipherResult.Success)
            val decrypted = (decryptResult as com.transcripto.domain.model.CipherResult.Success).data
            
            // Verify round-trip
            when (method) {
                "CAESAR", "VIGENERE" -> assertEquals(text.toUpperCase(), decrypted)
                else -> assertEquals(text, decrypted)
            }
        }
    }

    @Test
    fun `envelope detection works correctly`() {
        val envelopeParser = EnvelopeParserImpl()
        val detectAndParseEnvelope = DetectAndParseEnvelope(envelopeParser)

        // Valid envelope
        val validEnvelope = "method=CAESAR;salt=YWJjMTIz;payload=KHOOR"
        val result1 = detectAndParseEnvelope.execute(validEnvelope)
        assertTrue(result1 is com.transcripto.domain.usecase.EnvelopeResult.Success)

        // Invalid formats
        val invalidFormats = listOf(
            "Hello World",
            "method=CAESAR",
            "payload=KHOOR",
            "method=CAESAR;salt=YWJjMTIz",
            "",
            "invalid format"
        )

        invalidFormats.forEach { invalid ->
            val result = detectAndParseEnvelope.execute(invalid)
            assertTrue("Should not detect as envelope: $invalid", 
                result is com.transcripto.domain.usecase.EnvelopeResult.NotEnvelope || 
                result is com.transcripto.domain.usecase.EnvelopeResult.ParseError)
        }
    }
}