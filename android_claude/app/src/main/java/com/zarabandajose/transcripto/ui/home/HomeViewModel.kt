package com.zarabandajose.transcripto.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zarabandajose.transcripto.core.common.Result
import com.zarabandajose.transcripto.domain.model.CipherMethod
import com.zarabandajose.transcripto.domain.model.CipherParams
import com.zarabandajose.transcripto.domain.usecase.DecryptTextUseCase
import com.zarabandajose.transcripto.domain.usecase.EncryptTextUseCase
import com.zarabandajose.transcripto.domain.usecase.GenerateSaltUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla principal de Transcripto.
 * Maneja el estado de la UI y las operaciones de cifrado/descifrado.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val encryptTextUseCase: EncryptTextUseCase,
    private val decryptTextUseCase: DecryptTextUseCase,
    private val generateSaltUseCase: GenerateSaltUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    /**
     * Actualiza el texto de entrada.
     */
    fun updateInputText(text: String) {
        _uiState.value = _uiState.value.copy(
            inputText = text,
            showStatus = false
        )
    }
    
    /**
     * Actualiza el método de cifrado seleccionado.
     */
    fun updateSelectedMethod(method: CipherMethod) {
        _uiState.value = _uiState.value.copy(
            selectedMethod = method,
            showStatus = false
        )
    }
    
    /**
     * Cambia entre modo cifrar y descifrar.
     */
    fun updateMode(isEncryptMode: Boolean) {
        _uiState.value = _uiState.value.copy(
            isEncryptMode = isEncryptMode,
            showStatus = false
        )
    }
    
    /**
     * Actualiza la clave para Vigenère y XOR.
     */
    fun updateKey(key: String) {
        _uiState.value = _uiState.value.copy(
            key = key,
            showStatus = false
        )
    }
    
    /**
     * Actualiza el desplazamiento para César.
     */
    fun updateShift(shift: String) {
        _uiState.value = _uiState.value.copy(
            shift = shift,
            showStatus = false
        )
    }
    
    /**
     * Actualiza si se debe usar salt.
     */
    fun updateUseSalt(useSalt: Boolean) {
        _uiState.value = _uiState.value.copy(
            useSalt = useSalt,
            showStatus = false
        )
        
        // Si se activa salt y está en modo automático, generar uno nuevo
        if (useSalt && _uiState.value.autoGenerateSalt) {
            generateNewSalt()
        }
    }
    
    /**
     * Actualiza el modo de generación de salt (automático/manual).
     */
    fun updateAutoGenerateSalt(autoGenerate: Boolean) {
        _uiState.value = _uiState.value.copy(
            autoGenerateSalt = autoGenerate,
            showStatus = false
        )
        
        // Si se cambia a automático, generar un nuevo salt
        if (autoGenerate) {
            generateNewSalt()
        }
    }
    
    /**
     * Actualiza el salt manual.
     */
    fun updateManualSalt(salt: String) {
        _uiState.value = _uiState.value.copy(
            manualSalt = salt,
            showStatus = false
        )
    }
    
    /**
     * Genera un nuevo salt automático.
     */
    private fun generateNewSalt() {
        val newSalt = generateSaltUseCase()
        _uiState.value = _uiState.value.copy(manualSalt = newSalt)
    }
    
    /**
     * Procesa el texto (cifrar o descifrar).
     */
    fun processText() {
        val currentState = _uiState.value
        
        if (!currentState.isValidConfiguration) {
            showError("Configuración inválida. Verifique los parámetros.")
            return
        }
        
        _uiState.value = currentState.copy(isProcessing = true, showStatus = false)
        
        viewModelScope.launch {
            val params = createCipherParams(currentState)
            
            val result = if (currentState.isEncryptMode) {
                encryptTextUseCase(currentState.inputText, params)
            } else {
                decryptTextUseCase(currentState.inputText, params)
            }
            
            when (result) {
                is Result.Success -> {
                    // Extraer solo el payload para mostrar al usuario
                    val displayText = if (currentState.isEncryptMode) {
                        extractPayloadFromEnvelope(result.data)
                    } else {
                        result.data // En descifrado ya es solo el payload
                    }
                    
                    _uiState.value = _uiState.value.copy(
                        outputText = displayText,
                        statusMessage = "Operación completada exitosamente",
                        isError = false,
                        showStatus = true,
                        isProcessing = false
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        statusMessage = result.message,
                        isError = true,
                        showStatus = true,
                        isProcessing = false
                    )
                }
            }
        }
    }
    
    /**
     * Crea los parámetros de cifrado basados en el estado actual.
     */
    private fun createCipherParams(state: HomeUiState): CipherParams {
        val effectiveSalt = if (state.useSalt) {
            if (state.autoGenerateSalt && state.manualSalt.isEmpty()) {
                generateSaltUseCase()
            } else {
                state.manualSalt
            }
        } else {
            ""
        }
        
        return CipherParams(
            method = state.selectedMethod,
            key = state.key,
            shift = state.shift.toIntOrNull() ?: 0,
            salt = effectiveSalt,
            useSalt = state.useSalt
        )
    }
    
    /**
     * Limpia todos los campos.
     */
    fun clearAll() {
        _uiState.value = HomeUiState()
    }
    
    /**
     * Oculta el mensaje de estado.
     */
    fun hideStatus() {
        _uiState.value = _uiState.value.copy(showStatus = false)
    }
    
    /**
     * Muestra un mensaje de error.
     */
    private fun showError(message: String) {
        _uiState.value = _uiState.value.copy(
            statusMessage = message,
            isError = true,
            showStatus = true,
            isProcessing = false
        )
    }
    
    /**
     * Extrae solo el payload del envelope para mostrar al usuario.
     * Si el texto no tiene formato envelope, lo devuelve tal como está.
     */
    private fun extractPayloadFromEnvelope(text: String): String {
        // Verificar si tiene formato envelope: method=...;salt=...;payload=...
        if (text.contains("method=") && text.contains("salt=") && text.contains("payload=")) {
            val payloadIndex = text.indexOf("payload=")
            if (payloadIndex != -1) {
                return text.substring(payloadIndex + 8) // 8 es la longitud de "payload="
            }
        }
        // Si no es envelope, devolver el texto original
        return text
    }
}
