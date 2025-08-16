package com.zarabandajose.transcripto.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zarabandajose.transcripto.app.factory.CipherMethodFactory
import com.zarabandajose.transcripto.core.common.Envelope
import com.zarabandajose.transcripto.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TranscriptoViewModel @Inject constructor(
    private val saltProvider: SaltProvider,
    private val cipherMethodFactory: CipherMethodFactory
) : ViewModel() {

    var uiState by mutableStateOf(TranscriptoUiState())
        private set

    init {
        validateKey(uiState.key, uiState.method)
    }

    private fun validateKey(key: String, method: CipherMethodType) {
        val keyNeedsValidation = when (method) {
            CipherMethodType.Caesar, CipherMethodType.Vigenere, CipherMethodType.XOR -> true
            CipherMethodType.Base64 -> false
        }

        val isKeyValid = if (keyNeedsValidation) {
            when (method) {
                CipherMethodType.Caesar -> key.toIntOrNull() != null
                CipherMethodType.Vigenere, CipherMethodType.XOR -> key.isNotEmpty()
                CipherMethodType.Base64 -> true // No necesita validación, siempre es válido
            }
        } else {
            true
        }
        uiState = uiState.copy(isKeyValid = isKeyValid, keyNeedsValidation = keyNeedsValidation)
    }

    fun onInputChanged(input: String) {
        uiState = uiState.copy(inputText = input)
    }

    fun onModeChanged(mode: Mode) {
        uiState = uiState.copy(mode = mode)
    }

    fun onMethodChanged(method: CipherMethodType) {
        uiState = uiState.copy(method = method)
        validateKey(uiState.key, method)
    }

    fun onKeyChanged(key: String) {
        uiState = uiState.copy(key = key)
        validateKey(key, uiState.method)
    }

    fun onUseSaltChanged(useSalt: Boolean) {
        uiState = uiState.copy(useSalt = useSalt)
    }

    fun onSaltChanged(salt: String) {
        uiState = uiState.copy(salt = salt)
    }

    fun onGenerateSalt() {
        uiState = uiState.copy(salt = saltProvider.generateSalt())
    }

    fun onProcess() {
        val (inputText, mode, method, key, useSalt, salt) = uiState
        val cipherMethod = cipherMethodFactory.create(method)

        when (mode) {
            Mode.ENCRYPT -> {
                val params = EncryptionParams(method, key, if (useSalt) salt else null)
                val encrypted = cipherMethod.encrypt(inputText, params)
                val envelope = Envelope.pack(method::class.java.simpleName, if (useSalt) salt else null, encrypted)
                uiState = uiState.copy(outputText = encrypted, outputEnvelope = envelope)
            }
            Mode.DECRYPT -> {
                val (parsedMethodName, parsedSalt, payload) = Envelope.unpack(inputText) ?: Triple(method::class.java.simpleName, salt, inputText)
                val actualMethod = when(parsedMethodName) {
                    "Caesar" -> CipherMethodType.Caesar
                    "Base64" -> CipherMethodType.Base64
                    "Vigenere" -> CipherMethodType.Vigenere
                    "XOR" -> CipherMethodType.XOR
                    else -> method
                }
                val actualCipher = cipherMethodFactory.create(actualMethod)
                val params = DecryptionParams(actualMethod, key, parsedSalt)
                val decrypted = actualCipher.decrypt(payload, params)
                uiState = uiState.copy(outputText = decrypted, outputEnvelope = "")
            }
        }
    }

    fun onClear() {
        uiState = TranscriptoUiState()
    }
}

data class TranscriptoUiState(
    val inputText: String = "",
    val mode: Mode = Mode.ENCRYPT,
    val method: CipherMethodType = CipherMethodType.Caesar,
    val key: String = "",
    val useSalt: Boolean = false,
    val salt: String = "",
    val outputText: String = "",
    val outputEnvelope: String = "",
    val isKeyValid: Boolean = true,
    val keyNeedsValidation: Boolean = true
)

enum class Mode {
    ENCRYPT, DECRYPT
}
