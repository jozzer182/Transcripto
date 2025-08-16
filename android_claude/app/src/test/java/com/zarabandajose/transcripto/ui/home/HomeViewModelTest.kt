package com.zarabandajose.transcripto.ui.home

import com.google.common.truth.Truth.assertThat
import com.zarabandajose.transcripto.core.common.Result
import com.zarabandajose.transcripto.domain.model.CipherMethod
import com.zarabandajose.transcripto.domain.model.CipherParams
import com.zarabandajose.transcripto.domain.usecase.DecryptTextUseCase
import com.zarabandajose.transcripto.domain.usecase.EncryptTextUseCase
import com.zarabandajose.transcripto.domain.usecase.GenerateSaltUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val encryptTextUseCase: EncryptTextUseCase = mockk()
    private val decryptTextUseCase: DecryptTextUseCase = mockk()
    private val generateSaltUseCase: GenerateSaltUseCase = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        
        every { generateSaltUseCase() } returns "testSalt123"
        
        viewModel = HomeViewModel(
            encryptTextUseCase = encryptTextUseCase,
            decryptTextUseCase = decryptTextUseCase,
            generateSaltUseCase = generateSaltUseCase
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `encrypt text should show only payload without envelope metadata`() = runTest {
        // Given
        val inputText = "Hello World"
        val envelopeResult = "method=BASE64;salt=dGVzdFNhbHQxMjM=;payload=SGVsbG8gV29ybGQ="
        val expectedPayload = "SGVsbG8gV29ybGQ="
        
        coEvery { 
            encryptTextUseCase(any(), any()) 
        } returns Result.Success(envelopeResult)
        
        // When
        viewModel.updateInputText(inputText)
        viewModel.updateSelectedMethod(CipherMethod.BASE64)
        viewModel.updateMode(true) // encrypt mode
        viewModel.processText()
        
        advanceUntilIdle()
        
        // Then
        val currentState = viewModel.uiState.value
        assertThat(currentState.outputText).isEqualTo(expectedPayload)
        assertThat(currentState.outputText).doesNotContain("method=")
        assertThat(currentState.outputText).doesNotContain("salt=")
        assertThat(currentState.outputText).doesNotContain("payload=")
        assertThat(currentState.isError).isFalse()
        assertThat(currentState.showStatus).isTrue()
    }

    @Test
    fun `decrypt text should show decrypted payload directly`() = runTest {
        // Given
        val inputText = "method=BASE64;salt=dGVzdFNhbHQxMjM=;payload=SGVsbG8gV29ybGQ="
        val decryptedText = "Hello World"
        
        coEvery { 
            decryptTextUseCase(any(), any()) 
        } returns Result.Success(decryptedText)
        
        // When
        viewModel.updateInputText(inputText)
        viewModel.updateSelectedMethod(CipherMethod.BASE64)
        viewModel.updateMode(false) // decrypt mode
        viewModel.processText()
        
        advanceUntilIdle()
        
        // Then
        val currentState = viewModel.uiState.value
        assertThat(currentState.outputText).isEqualTo(decryptedText)
        assertThat(currentState.isError).isFalse()
        assertThat(currentState.showStatus).isTrue()
    }

    @Test
    fun `encrypt with Caesar cipher should show only encrypted text`() = runTest {
        // Given
        val inputText = "HELLO"
        val envelopeResult = "method=CAESAR;salt=;payload=KHOOR"
        val expectedPayload = "KHOOR"
        
        coEvery { 
            encryptTextUseCase(any(), any()) 
        } returns Result.Success(envelopeResult)
        
        // When
        viewModel.updateInputText(inputText)
        viewModel.updateSelectedMethod(CipherMethod.CAESAR)
        viewModel.updateShift("3")
        viewModel.updateMode(true) // encrypt mode
        viewModel.processText()
        
        advanceUntilIdle()
        
        // Then
        val currentState = viewModel.uiState.value
        assertThat(currentState.outputText).isEqualTo(expectedPayload)
        assertThat(currentState.outputText).doesNotContain("method=CAESAR")
        assertThat(currentState.isError).isFalse()
    }

    @Test
    fun `process text with error should show error message`() = runTest {
        // Given
        val errorMessage = "Encryption failed"
        coEvery { 
            encryptTextUseCase(any(), any()) 
        } returns Result.Error(errorMessage)
        
        // When
        viewModel.updateInputText("test")
        viewModel.updateSelectedMethod(CipherMethod.BASE64)
        viewModel.updateMode(true)
        viewModel.processText()
        
        advanceUntilIdle()
        
        // Then
        val currentState = viewModel.uiState.value
        assertThat(currentState.statusMessage).isEqualTo(errorMessage)
        assertThat(currentState.isError).isTrue()
        assertThat(currentState.showStatus).isTrue()
        assertThat(currentState.isProcessing).isFalse()
    }

    @Test
    fun `updateInputText should clear status`() {
        // Given
        viewModel.updateInputText("initial")
        
        // When
        viewModel.updateInputText("new text")
        
        // Then
        val currentState = viewModel.uiState.value
        assertThat(currentState.inputText).isEqualTo("new text")
        assertThat(currentState.showStatus).isFalse()
    }

    @Test
    fun `clearAll should reset to initial state`() {
        // Given
        viewModel.updateInputText("test")
        viewModel.updateSelectedMethod(CipherMethod.CAESAR)
        viewModel.updateKey("testkey")
        
        // When
        viewModel.clearAll()
        
        // Then
        val currentState = viewModel.uiState.value
        assertThat(currentState.inputText).isEmpty()
        assertThat(currentState.selectedMethod).isEqualTo(CipherMethod.CAESAR)
        assertThat(currentState.key).isEmpty()
        assertThat(currentState.outputText).isEmpty()
    }
}
