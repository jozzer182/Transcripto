package com.zarabandajose.transcripto.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.zarabandajose.transcripto.core.common.Result
import com.zarabandajose.transcripto.domain.model.CipherMethod
import com.zarabandajose.transcripto.domain.model.EnvelopeData
import org.junit.jupiter.api.Test

class DetectAndParseEnvelopeUseCaseTest {
    
    private val detectAndParseEnvelopeUseCase = DetectAndParseEnvelopeUseCase()
    
    @Test
    fun `parses valid envelope correctly`() {
        // Given
        val envelope = "method=CAESAR;salt=dGVzdA==;payload=Khoor"
        
        // When
        val result = detectAndParseEnvelopeUseCase(envelope)
        
        // Then
        assertThat(result).isInstanceOf(Result.Success::class.java)
        val data = (result as Result.Success).data
        assertThat(data.method).isEqualTo(CipherMethod.CAESAR)
        assertThat(data.salt).isEqualTo("test")
        assertThat(data.payload).isEqualTo("Khoor")
    }
    
    @Test
    fun `parses envelope with empty salt`() {
        // Given
        val envelope = "method=BASE64;salt=;payload=SGVsbG8="
        
        // When
        val result = detectAndParseEnvelopeUseCase(envelope)
        
        // Then
        assertThat(result).isInstanceOf(Result.Success::class.java)
        val data = (result as Result.Success).data
        assertThat(data.method).isEqualTo(CipherMethod.BASE64)
        assertThat(data.salt).isEmpty()
        assertThat(data.payload).isEqualTo("SGVsbG8=")
    }
    
    @Test
    fun `returns error for non-envelope text`() {
        // Given
        val regularText = "This is just regular text"
        
        // When
        val result = detectAndParseEnvelopeUseCase(regularText)
        
        // Then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).message).contains("No es formato envelope")
    }
    
    @Test
    fun `returns error for invalid method`() {
        // Given
        val invalidEnvelope = "method=INVALID_METHOD;salt=;payload=test"
        
        // When
        val result = detectAndParseEnvelopeUseCase(invalidEnvelope)
        
        // Then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).message).contains("Método de cifrado desconocido")
    }
    
    @Test
    fun `returns error for malformed envelope`() {
        // Given
        val malformedEnvelope = "method=CAESAR;missing_parts"
        
        // When
        val result = detectAndParseEnvelopeUseCase(malformedEnvelope)
        
        // Then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).message).contains("Formato envelope inválido")
    }
    
    @Test
    fun `returns error for invalid base64 salt`() {
        // Given
        val invalidEnvelope = "method=CAESAR;salt=invalid_base64!;payload=test"
        
        // When
        val result = detectAndParseEnvelopeUseCase(invalidEnvelope)
        
        // Then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).message).contains("Salt en Base64 inválido")
    }
    
    @Test
    fun `returns error for empty payload`() {
        // Given
        val emptyPayloadEnvelope = "method=CAESAR;salt=;payload="
        
        // When
        val result = detectAndParseEnvelopeUseCase(emptyPayloadEnvelope)
        
        // Then
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).message).contains("Payload no puede estar vacío")
    }
}
