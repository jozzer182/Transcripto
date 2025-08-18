package com.transcripto.domain.usecase

import com.transcripto.domain.repository.SaltProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class GenerateSaltTest {

    private lateinit var generateSalt: GenerateSalt
    private lateinit var mockSaltProvider: SaltProvider

    @Before
    fun setup() {
        mockSaltProvider = mock()
        generateSalt = GenerateSalt(mockSaltProvider)
    }

    @Test
    fun `execute with default length returns generated salt`() {
        val expectedSalt = "YWJjMTIz"
        whenever(mockSaltProvider.generateSalt(16)).thenReturn(expectedSalt)

        val result = generateSalt.execute()

        assertEquals(expectedSalt, result)
        verify(mockSaltProvider).generateSalt(16)
    }

    @Test
    fun `execute with custom length returns generated salt`() {
        val length = 32
        val expectedSalt = "ZGVmNDU2"
        whenever(mockSaltProvider.generateSalt(length)).thenReturn(expectedSalt)

        val result = generateSalt.execute(length)

        assertEquals(expectedSalt, result)
        verify(mockSaltProvider).generateSalt(length)
    }

    @Test
    fun `execute with zero length returns empty salt`() {
        val expectedSalt = ""
        whenever(mockSaltProvider.generateSalt(0)).thenReturn(expectedSalt)

        val result = generateSalt.execute(0)

        assertEquals(expectedSalt, result)
        verify(mockSaltProvider).generateSalt(0)
    }

    @Test
    fun `execute with negative length returns empty salt`() {
        val expectedSalt = ""
        whenever(mockSaltProvider.generateSalt(-1)).thenReturn(expectedSalt)

        val result = generateSalt.execute(-1)

        assertEquals(expectedSalt, result)
        verify(mockSaltProvider).generateSalt(-1)
    }

    @Test
    fun `execute propagates salt provider error`() {
        val exception = RuntimeException("Salt generation failed")
        whenever(mockSaltProvider.generateSalt(16)).thenThrow(exception)

        try {
            generateSalt.execute()
            fail("Expected exception to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(exception, e)
        }
    }

    @Test
    fun `execute with different lengths calls provider correctly`() {
        val lengths = listOf(8, 16, 32, 64)
        
        lengths.forEach { length ->
            whenever(mockSaltProvider.generateSalt(length)).thenReturn("salt$length")
            
            val result = generateSalt.execute(length)
            
            assertEquals("salt$length", result)
            verify(mockSaltProvider).generateSalt(length)
        }
    }
}