package com.transcripto.domain.usecase

import com.transcripto.domain.model.EnvelopeData
import com.transcripto.domain.repository.EnvelopeParser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class DetectAndParseEnvelopeTest {

    private lateinit var detectAndParseEnvelope: DetectAndParseEnvelope
    private lateinit var mockEnvelopeParser: EnvelopeParser

    @Before
    fun setup() {
        mockEnvelopeParser = mock()
        detectAndParseEnvelope = DetectAndParseEnvelope(mockEnvelopeParser)
    }

    @Test
    fun `execute with valid envelope returns parsed data`() {
        val envelope = "method=CAESAR;salt=YWJjMTIz;payload=Khoor#Zruog"
        val expectedData = EnvelopeData("CAESAR", "YWJjMTIz", "Khoor#Zruog")

        whenever(mockEnvelopeParser.isEnvelopeFormat(envelope)).thenReturn(true)
        whenever(mockEnvelopeParser.parse(envelope)).thenReturn(expectedData)

        val result = detectAndParseEnvelope.execute(envelope)

        assertTrue(result is EnvelopeResult.Success)
        assertEquals(expectedData, (result as EnvelopeResult.Success).data)
        verify(mockEnvelopeParser).isEnvelopeFormat(envelope)
        verify(mockEnvelopeParser).parse(envelope)
    }

    @Test
    fun `execute with non-envelope text returns not envelope`() {
        val text = "Hello World"

        whenever(mockEnvelopeParser.isEnvelopeFormat(text)).thenReturn(false)

        val result = detectAndParseEnvelope.execute(text)

        assertTrue(result is EnvelopeResult.NotEnvelope)
        verify(mockEnvelopeParser).isEnvelopeFormat(text)
        verify(mockEnvelopeParser, never()).parse(any())
    }

    @Test
    fun `execute with invalid envelope format returns parse error`() {
        val invalidEnvelope = "method=CAESAR;salt=YWJjMTIz"

        whenever(mockEnvelopeParser.isEnvelopeFormat(invalidEnvelope)).thenReturn(true)
        whenever(mockEnvelopeParser.parse(invalidEnvelope)).thenReturn(null)

        val result = detectAndParseEnvelope.execute(invalidEnvelope)

        assertTrue(result is EnvelopeResult.ParseError)
        verify(mockEnvelopeParser).isEnvelopeFormat(invalidEnvelope)
        verify(mockEnvelopeParser).parse(invalidEnvelope)
    }

    @Test
    fun `execute with empty string returns not envelope`() {
        val result = detectAndParseEnvelope.execute("")

        assertTrue(result is EnvelopeResult.NotEnvelope)
        verify(mockEnvelopeParser).isEnvelopeFormat("")
        verify(mockEnvelopeParser, never()).parse(any())
    }

    @Test
    fun `execute with blank string returns not envelope`() {
        val text = "   "

        whenever(mockEnvelopeParser.isEnvelopeFormat(text)).thenReturn(false)

        val result = detectAndParseEnvelope.execute(text)

        assertTrue(result is EnvelopeResult.NotEnvelope)
        verify(mockEnvelopeParser).isEnvelopeFormat(text)
    }

    @Test
    fun `execute with special characters in envelope returns success`() {
        val envelope = "method=VIGENERE;salt=YWJjMTIz!@#;payload=Khoor#Zruog$%^"
        val expectedData = EnvelopeData("VIGENERE", "YWJjMTIz!@#", "Khoor#Zruog$%^")

        whenever(mockEnvelopeParser.isEnvelopeFormat(envelope)).thenReturn(true)
        whenever(mockEnvelopeParser.parse(envelope)).thenReturn(expectedData)

        val result = detectAndParseEnvelope.execute(envelope)

        assertTrue(result is EnvelopeResult.Success)
        assertEquals(expectedData, (result as EnvelopeResult.Success).data)
    }
}