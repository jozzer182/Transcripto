package com.transcripto.data.crypto.parser

import com.transcripto.domain.model.EnvelopeData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EnvelopeParserImplTest {

    private lateinit var envelopeParser: EnvelopeParserImpl

    @Before
    fun setup() {
        envelopeParser = EnvelopeParserImpl()
    }

    @Test
    fun `parse valid envelope returns correct data`() {
        val envelope = "method=CAESAR;salt=YWJjMTIz;payload=Khoor#Zruog"
        val result = envelopeParser.parse(envelope)

        assertNotNull(result)
        assertEquals("CAESAR", result?.method)
        assertEquals("YWJjMTIz", result?.salt)
        assertEquals("Khoor#Zruog", result?.payload)
    }

    @Test
    fun `parse envelope without salt returns correct data`() {
        val envelope = "method=BASE64;salt=;payload=SGVsbG8="
        val result = envelopeParser.parse(envelope)

        assertNotNull(result)
        assertEquals("BASE64", result?.method)
        assertEquals("", result?.salt)
        assertEquals("SGVsbG8=", result?.payload)
    }

    @Test
    fun `parse invalid format returns null`() {
        val envelope = "invalid-format"
        val result = envelopeParser.parse(envelope)

        assertNull(result)
    }

    @Test
    fun `parse missing method returns null`() {
        val envelope = "salt=YWJjMTIz;payload=Khoor#Zruog"
        val result = envelopeParser.parse(envelope)

        assertNull(result)
    }

    @Test
    fun `parse missing payload returns null`() {
        val envelope = "method=CAESAR;salt=YWJjMTIz"
        val result = envelopeParser.parse(envelope)

        assertNull(result)
    }

    @Test
    fun `create envelope returns correct format`() {
        val data = EnvelopeData("CAESAR", "YWJjMTIz", "Khoor#Zruog")
        val envelope = envelopeParser.create(data)

        assertEquals("method=CAESAR;salt=YWJjMTIz;payload=Khoor#Zruog", envelope)
    }

    @Test
    fun `create envelope without salt returns correct format`() {
        val data = EnvelopeData("BASE64", "", "SGVsbG8=")
        val envelope = envelopeParser.create(data)

        assertEquals("method=BASE64;salt=;payload=SGVsbG8=", envelope)
    }

    @Test
    fun `isEnvelopeFormat detects valid format correctly`() {
        assertTrue(envelopeParser.isEnvelopeFormat("method=CAESAR;salt=YWJjMTIz;payload=Khoor#Zruog"))
        assertTrue(envelopeParser.isEnvelopeFormat("method=BASE64;salt=;payload=SGVsbG8="))
    }

    @Test
    fun `isEnvelopeFormat returns false for invalid format`() {
        assertFalse(envelopeParser.isEnvelopeFormat("invalid"))
        assertFalse(envelopeParser.isEnvelopeFormat("method=CAESAR"))
        assertFalse(envelopeParser.isEnvelopeFormat("Hello World"))
        assertFalse(envelopeParser.isEnvelopeFormat(""))
    }

    @Test
    fun `isEnvelopeFormat handles edge cases`() {
        assertFalse(envelopeParser.isEnvelopeFormat("method=;salt=;payload="))
        assertFalse(envelopeParser.isEnvelopeFormat("method=CAESAR;salt=YWJjMTIz"))
    }
}