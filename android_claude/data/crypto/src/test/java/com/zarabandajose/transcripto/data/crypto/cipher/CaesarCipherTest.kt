package com.zarabandajose.transcripto.data.crypto.cipher

import com.google.common.truth.Truth.assertThat
import com.zarabandajose.transcripto.core.common.Result
import com.zarabandajose.transcripto.domain.model.CipherMethod
import com.zarabandajose.transcripto.domain.model.CipherParams
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CaesarCipherTest {
    
    private val caesarCipher = CaesarCipher()
    
    @Test
    fun `encrypt with shift 3 returns correct result`() {
        // Given
        val input = "Hello World"
        val params = CipherParams(
            method = CipherMethod.CAESAR,
            shift = 3
        )
        
        // When
        val result = caesarCipher.encrypt(input, params)
        
        // Then
        assertThat(result).isInstanceOf(Result.Success::class.java)
        val encrypted = (result as Result.Success).data
        assertThat(encrypted).isEqualTo("Khoor Zruog")
    }
    
    @Test
    fun `decrypt with shift 3 returns original text`() {
        // Given
        val encrypted = "Khoor Zruog"
        val params = CipherParams(
            method = CipherMethod.CAESAR,
            shift = 3
        )
        
        // When
        val result = caesarCipher.decrypt(encrypted, params)
        
        // Then
        assertThat(result).isInstanceOf(Result.Success::class.java)
        val decrypted = (result as Result.Success).data
        assertThat(decrypted).isEqualTo("Hello World")
    }
    
    @ParameterizedTest
    @ValueSource(ints = [1, 5, 13, 25, -1, -5, -13, -25])
    fun `encrypt and decrypt should be reversible for different shifts`(shift: Int) {
        // Given
        val original = "The Quick Brown Fox Jumps Over The Lazy Dog"
        val encryptParams = CipherParams(method = CipherMethod.CAESAR, shift = shift)
        val decryptParams = CipherParams(method = CipherMethod.CAESAR, shift = shift)
        
        // When
        val encrypted = caesarCipher.encrypt(original, encryptParams)
        val decrypted = caesarCipher.decrypt((encrypted as Result.Success).data, decryptParams)
        
        // Then
        assertThat(decrypted).isInstanceOf(Result.Success::class.java)
        assertThat((decrypted as Result.Success).data).isEqualTo(original)
    }
    
    @Test
    fun `preserves non-alphabetic characters`() {
        // Given
        val input = "Hello, World! 123 #@$"
        val params = CipherParams(method = CipherMethod.CAESAR, shift = 1)
        
        // When
        val result = caesarCipher.encrypt(input, params)
        
        // Then
        val encrypted = (result as Result.Success).data
        assertThat(encrypted).isEqualTo("Ifmmp, Xpsme! 123 #@$")
    }
    
    @Test
    fun `handles wrap-around correctly`() {
        // Given
        val input = "XYZ xyz"
        val params = CipherParams(method = CipherMethod.CAESAR, shift = 3)
        
        // When
        val result = caesarCipher.encrypt(input, params)
        
        // Then
        val encrypted = (result as Result.Success).data
        assertThat(encrypted).isEqualTo("ABC abc")
    }
    
    @Test
    fun `encrypt and decrypt with salt modifies shift correctly`() {
        // Given
        val input = "Hello"
        val salt = "test"
        val shift = 3
        val params = CipherParams(
            method = CipherMethod.CAESAR,
            shift = shift,
            salt = salt,
            useSalt = true
        )
        
        // When - encrypt with salt
        val encrypted = caesarCipher.encrypt(input, params)
        val decrypted = caesarCipher.decrypt((encrypted as Result.Success).data, params)
        
        // Then - should be reversible even with salt
        assertThat(decrypted).isInstanceOf(Result.Success::class.java)
        assertThat((decrypted as Result.Success).data).isEqualTo(input)
    }
}
