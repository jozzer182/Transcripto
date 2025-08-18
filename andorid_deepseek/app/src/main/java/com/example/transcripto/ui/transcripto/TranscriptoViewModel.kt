package com.example.transcripto.ui.transcripto

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transcripto.domain.crypto.CipherMethod
import com.example.transcripto.domain.usecases.DecryptText
import com.example.transcripto.domain.usecases.DetectAndParseEnvelope
import com.example.transcripto.domain.usecases.EncryptText
import com.example.transcripto.domain.usecases.GenerateSalt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranscriptoViewModel @Inject constructor(
    private val encryptText: EncryptText,
    private val decryptText: DecryptText,
    private val detectAndParseEnvelope: DetectAndParseEnvelope,
    private val generateSalt: GenerateSalt
) : ViewModel() {
    
    var uiState by mutableStateOf(TranscriptoUiState())
        private set
    
    fun onInputChange(text: String) {
        uiState = uiState.copy(inputText = text)
        
        // Auto-detect envelope format
        if (text.startsWith("method=")) {
            detectAndParseEnvelope(text)?.let { (method, params) ->
                uiState = uiState.copy(
                    selectedMethod = method,
                    key = params.key,
                    shift = params.shift,
                    useSalt = params.salt.isNotEmpty(),
                    salt = params.salt,
                    saltMode = if (params.salt.isNotEmpty()) SaltMode.Manual else SaltMode.None
                )
            }
        }
    }
    
    fun onMethodChange(method: CipherMethod) {
        uiState = uiState.copy(selectedMethod = method)
    }
    
    fun onModeChange(isEncrypt: Boolean) {
        uiState = uiState.copy(isEncryptMode = isEncrypt)
    }
    
    fun onKeyChange(key: String) {
        uiState = uiState.copy(key = key)
    }
    
    fun onShiftChange(shift: Int) {
        uiState = uiState.copy(shift = shift)
    }
    
    fun onUseSaltChange(useSalt: Boolean) {
        uiState = uiState.copy(
            useSalt = useSalt,
            saltMode = if (useSalt) SaltMode.Auto else SaltMode.None
        )
    }
    
    fun onSaltModeChange(mode: SaltMode) {
        uiState = uiState.copy(saltMode = mode)
        if (mode == SaltMode.Auto) {
            uiState = uiState.copy(salt = generateSalt())
        }
    }
    
    fun onSaltChange(salt: String) {
        uiState = uiState.copy(salt = salt)
    }
    
    fun processText() {
        viewModelScope.launch {
            try {
                val params = CipherMethod.CipherParams(
                    key = uiState.key,
                    shift = uiState.shift,
                    salt = if (uiState.useSalt) uiState.salt else ""
                )
                
                val result = if (uiState.isEncryptMode) {
                    encryptText(uiState.inputText, uiState.selectedMethod, params)
                } else {
                    decryptText(uiState.inputText, uiState.selectedMethod, params)
                }
                
                uiState = uiState.copy(
                    outputText = result,
                    errorMessage = null,
                    showResult = true
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    errorMessage = e.message ?: "Error processing text",
                    showResult = false
                )
            }
        }
    }
    
    fun clearFields() {
        uiState = uiState.copy(
            inputText = "",
            outputText = "",
            errorMessage = null,
            showResult = false
        )
    }
}

data class TranscriptoUiState(
    val inputText: String = "",
    val outputText: String = "",
    val errorMessage: String? = null,
    val showResult: Boolean = false,
    val selectedMethod: CipherMethod = CipherMethod.Base64,
    val isEncryptMode: Boolean = true,
    val key: String = "",
    val shift: Int = 3,
    val useSalt: Boolean = false,
    val salt: String = "",
    val saltMode: SaltMode = SaltMode.None
)

enum class SaltMode {
    None, Auto, Manual
}