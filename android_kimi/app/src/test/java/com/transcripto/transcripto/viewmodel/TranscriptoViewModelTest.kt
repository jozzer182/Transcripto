package com.transcripto.transcripto.viewmodel

import com.transcripto.domain.model.CipherResult
import com.transcripto.domain.model.EnvelopeData
import com.transcripto.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class TranscriptoViewModelTest {

    private lateinit var viewModel: TranscriptoViewModel
    private lateinit var mockEncryptText: EncryptText
    private lateinit var mockDecryptText: DecryptText
    private lateinit var mockDetectAndParseEnvelope: DetectAndParseEnvelope
    private lateinit var mockGenerateSalt: GenerateSalt
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setup() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        
        mockEncryptText = mock()
        mockDecryptText = mock()
        mockDetectAndParseEnvelope = mock()
        mockGenerateSalt = mock()
        
        viewModel = TranscriptoViewModel(
            encryptText = mockEncryptText,
            decryptText = mockDecryptText,
            detectAndParseEnvelope = mockDetectAndParseEnvelope,
            generateSalt = mockGenerateSalt
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() {
        val state = viewModel.state.value
        
        assertEquals("", state.inputText)
        assertEquals("", state.outputText)
        assertEquals("", state.key)
        assertEquals("", state.salt)
        assertEquals("CAESAR", state.selectedMethod)
        assertEquals(false, state.isEncryptMode)
        assertEquals(false, state.showSalt)
        assertEquals(false, state.isLoading)
        assertEquals(null, state.errorMessage)
    }

    @Test
    fun `onInputTextChanged updates input text`() {
        val newText = "Hello World"
        
        viewModel.onAction(TranscriptoAction.InputTextChanged(newText))
        
        assertEquals(newText, viewModel.state.value.inputText)
    }

    @Test
    fun `onKeyChanged updates key`() {
        val newKey = "secret"
        
        viewModel.onAction(TranscriptoAction.KeyChanged(newKey))
        
        assertEquals(newKey, viewModel.state.value.key)
    }

    @Test
    fun `onMethodChanged updates selected method`() {
        val newMethod = "VIGENERE"
        
        viewModel.onAction(TranscriptoAction.MethodChanged(newMethod))
        
        assertEquals(newMethod, viewModel.state.value.selectedMethod)
    }

    @Test
    fun `onToggleMode switches encrypt decrypt mode`() {
        val initialMode = viewModel.state.value.isEncryptMode
        
        viewModel.onAction(TranscriptoAction.ToggleMode)
        
        assertEquals(!initialMode, viewModel.state.value.isEncryptMode)
    }

    @Test
    fun `onGenerateSalt generates new salt`() = runTest {
        val expectedSalt = "abc123"
        whenever(mockGenerateSalt.execute()).thenReturn(expectedSalt)
        
        viewModel.onAction(TranscriptoAction.GenerateSalt)
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertEquals(expectedSalt, viewModel.state.value.salt)
        verify(mockGenerateSalt).execute()
    }

    @Test
    fun `onProcessText encrypts successfully`() = runTest {
        val inputText = "HELLO"
        val key = "3"
        val method = "CAESAR"
        val expectedOutput = "KHOOR"
        
        viewModel.onAction(TranscriptoAction.InputTextChanged(inputText))
        viewModel.onAction(TranscriptoAction.KeyChanged(key))
        viewModel.onAction(TranscriptoAction.MethodChanged(method))
        
        whenever(mockEncryptText.execute(method, inputText, key, ""))
            .thenReturn(CipherResult.Success(expectedOutput))
        
        viewModel.onAction(TranscriptoAction.ProcessText)
        testDispatcher.scheduler.advanceUntilIdle()
        
        val state = viewModel.state.value
        assertEquals(expectedOutput, state.outputText)
        assertEquals(false, state.isLoading)
        assertEquals(null, state.errorMessage)
    }

    @Test
    fun `onProcessText decrypts successfully`() = runTest {
        val inputText = "KHOOR"
        val key = "3"
        val method = "CAESAR"
        val expectedOutput = "HELLO"
        
        viewModel.onAction(TranscriptoAction.InputTextChanged(inputText))
        viewModel.onAction(TranscriptoAction.KeyChanged(key))
        viewModel.onAction(TranscriptoAction.MethodChanged(method))
        viewModel.onAction(TranscriptoAction.ToggleMode)
        
        whenever(mockDecryptText.execute(method, inputText, key, ""))
            .thenReturn(CipherResult.Success(expectedOutput))
        
        viewModel.onAction(TranscriptoAction.ProcessText)
        testDispatcher.scheduler.advanceUntilIdle()
        
        val state = viewModel.state.value
        assertEquals(expectedOutput, state.outputText)
        assertEquals(false, state.isLoading)
        assertEquals(null, state.errorMessage)
    }

    @Test
    fun `onProcessText handles encryption error`() = runTest {
        val inputText = "HELLO"
        val key = "invalid"
        val method = "CAESAR"
        val errorMessage = "Invalid key"
        
        viewModel.onAction(TranscriptoAction.InputTextChanged(inputText))
        viewModel.onAction(TranscriptoAction.KeyChanged(key))
        viewModel.onAction(TranscriptoAction.MethodChanged(method))
        
        whenever(mockEncryptText.execute(method, inputText, key, ""))
            .thenReturn(CipherResult.Error(errorMessage))
        
        viewModel.onAction(TranscriptoAction.ProcessText)
        testDispatcher.scheduler.advanceUntilIdle()
        
        val state = viewModel.state.value
        assertEquals("", state.outputText)
        assertEquals(false, state.isLoading)
        assertEquals(errorMessage, state.errorMessage)
    }

    @Test
    fun `onDetectEnvelope detects and parses envelope`() = runTest {
        val envelopeText = "method=CAESAR;salt=YWJjMTIz;payload=KHOOR"
        val expectedData = EnvelopeData("CAESAR", "YWJjMTIz", "KHOOR")
        
        whenever(mockDetectAndParseEnvelope.execute(envelopeText))
            .thenReturn(EnvelopeResult.Success(expectedData))
        
        viewModel.onAction(TranscriptoAction.DetectEnvelope(envelopeText))
        testDispatcher.scheduler.advanceUntilIdle()
        
        val state = viewModel.state.value
        assertEquals(expectedData.payload, state.inputText)
        assertEquals(expectedData.method, state.selectedMethod)
        assertEquals(expectedData.salt, state.salt)
        assertEquals(false, state.isEncryptMode)
    }

    @Test
    fun `onDetectEnvelope handles non-envelope text`() = runTest {
        val text = "Hello World"
        
        whenever(mockDetectAndParseEnvelope.execute(text))
            .thenReturn(EnvelopeResult.NotEnvelope)
        
        viewModel.onAction(TranscriptoAction.DetectEnvelope(text))
        testDispatcher.scheduler.advanceUntilIdle()
        
        val state = viewModel.state.value
        assertEquals(text, state.inputText)
        assertEquals("CAESAR", state.selectedMethod)
        assertEquals("", state.salt)
    }

    @Test
    fun `onClearError clears error message`() {
        viewModel.onAction(TranscriptoAction.ProcessText) // This might set an error
        viewModel.onAction(TranscriptoAction.ClearError)
        
        assertEquals(null, viewModel.state.value.errorMessage)
    }
}