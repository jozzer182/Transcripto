package com.transcripto.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transcripto.domain.cipher.CipherMethod
import com.transcripto.domain.model.CipherParams
import com.transcripto.domain.usecase.DecryptText
import com.transcripto.domain.usecase.DetectAndParseEnvelope
import com.transcripto.domain.usecase.EncryptText
import com.transcripto.domain.usecase.GenerateSalt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Transcripto application.
 * Manages the state for encryption/decryption operations and user interactions.
 */
@HiltViewModel
class TranscriptoViewModel @Inject constructor(
    private val encryptText: EncryptText,
    private val decryptText: DecryptText,
    private val detectAndParseEnvelope: DetectAndParseEnvelope,
    private val generateSalt: GenerateSalt
) : ViewModel() {

    private val _uiState = MutableStateFlow(TranscriptoUiState())
    val uiState: StateFlow<TranscriptoUiState> = _uiState.asStateFlow()

    fun onInputTextChange(text: String) {
        _uiState.update { currentState ->
            currentState.copy(
                inputText = text,
                errorMessage = null
            )
        }
    }

    fun onKeyChange(key: String) {
        _uiState.update { currentState ->
            currentState.copy(
                key = key,
                errorMessage = null
            )
        }
    }

    fun onSelectedMethodChange(method: CipherMethod) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedMethod = method,
                errorMessage = null
            )
        }
    }

    fun onUseSaltChanged(useSalt: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                useSalt = useSalt,
                errorMessage = null
            )
        }
    }

    fun encryptText() {
        viewModelScope.launch {
            val currentState = _uiState.value
            
            if (currentState.inputText.isBlank()) {
                _uiState.update { it.copy(errorMessage = "Please enter text to encrypt") }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val salt = if (currentState.useSalt) {
                generateSalt()
            } else {
                null
            }

            val params = CipherParams(
                text = currentState.inputText,
                key = currentState.key,
                salt = salt
            )

            when (val result = encryptText(currentState.selectedMethod, params)) {
                is com.transcripto.domain.model.CipherResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            outputText = result.data,
                            isLoading = false,
                            lastOperation = OperationType.ENCRYPT
                        )
                    }
                }
                is com.transcripto.domain.model.CipherResult.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            errorMessage = result.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun decryptText() {
        viewModelScope.launch {
            val currentState = _uiState.value
            
            if (currentState.inputText.isBlank()) {
                _uiState.update { it.copy(errorMessage = "Please enter text to decrypt") }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val params = CipherParams(
                text = currentState.inputText,
                key = currentState.key
            )

            when (val result = decryptText(currentState.selectedMethod, params)) {
                is com.transcripto.domain.model.CipherResult.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            outputText = result.data,
                            isLoading = false,
                            lastOperation = OperationType.DECRYPT
                        )
                    }
                }
                is com.transcripto.domain.model.CipherResult.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            errorMessage = result.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun detectEnvelopeFormat() {
        viewModelScope.launch {
            val currentState = _uiState.value
            
            if (currentState.inputText.isBlank()) {
                _uiState.update { it.copy(errorMessage = "Please enter text to analyze") }
                return@launch
            }

            val envelopeData = detectAndParseEnvelope(currentState.inputText)
            
            if (envelopeData != null) {
                _uiState.update { state ->
                    state.copy(
                        detectedMethod = envelopeData.method,
                        detectedSalt = envelopeData.salt,
                        isEnvelopeDetected = true,
                        errorMessage = null
                    )
                }
            } else {
                _uiState.update { state ->
                    state.copy(
                        isEnvelopeDetected = false,
                        detectedMethod = null,
                        detectedSalt = null,
                        errorMessage = null
                    )
                }
            }
        }
    }

    fun clearAll() {
        _uiState.update { TranscriptoUiState() }
    }

    fun copyOutputToInput() {
        _uiState.update { currentState ->
            currentState.copy(
                inputText = currentState.outputText,
                outputText = "",
                errorMessage = null
            )
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

/**
 * Represents the UI state for the Transcripto application.
 */
data class TranscriptoUiState(
    val inputText: String = "",
    val outputText: String = "",
    val key: String = "",
    val selectedMethod: CipherMethod = CipherMethod.CAESAR,
    val useSalt: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val lastOperation: OperationType? = null,
    val isEnvelopeDetected: Boolean = false,
    val detectedMethod: CipherMethod? = null,
    val detectedSalt: String? = null
)

/**
 * Represents the type of the last operation performed.
 */
enum class OperationType {
    ENCRYPT,
    DECRYPT
}