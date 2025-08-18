package com.transcripto.data.crypto.provider

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SecureRandomSaltProviderTest {

    private lateinit var saltProvider: SecureRandomSaltProvider

    @Before
    fun setup() {
        saltProvider = SecureRandomSaltProvider()
    }

    @Test
    fun `generateSalt with valid length returns non-empty salt`() {
        val salt = saltProvider.generateSalt(16)
        
        assertNotNull(salt)
        assertTrue(salt.isNotEmpty())
        assertTrue(salt.length > 0)
    }

    @Test
    fun `generateSalt with different lengths returns different sized salts`() {
        val salt16 = saltProvider.generateSalt(16)
        val salt32 = saltProvider.generateSalt(32)
        
        assertNotNull(salt16)
        assertNotNull(salt32)
        
        // Base64 encoding increases size, so 32-byte salt will be longer
        assertTrue(salt32.length > salt16.length)
    }

    @Test
    fun `generateSalt returns unique values`() {
        val salt1 = saltProvider.generateSalt(16)
        val salt2 = saltProvider.generateSalt(16)
        
        assertNotNull(salt1)
        assertNotNull(salt2)
        assertNotEquals(salt1, salt2)
    }

    @Test
    fun `generateSalt with zero length returns empty string`() {
        val salt = saltProvider.generateSalt(0)
        
        assertEquals("", salt)
    }

    @Test
    fun `generateSalt with negative length returns empty string`() {
        val salt = saltProvider.generateSalt(-1)
        
        assertEquals("", salt)
    }

    @Test
    fun `generated salt is valid Base64`() {
        val salt = saltProvider.generateSalt(16)
        
        // Check if it's valid Base64 (basic check)
        assertTrue(salt.matches(Regex("^[A-Za-z0-9+/=]*$")))
    }
}