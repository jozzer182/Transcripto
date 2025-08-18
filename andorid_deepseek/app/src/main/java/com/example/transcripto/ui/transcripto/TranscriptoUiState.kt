package com.example.transcripto.ui.transcripto

import com.example.transcripto.domain.crypto.CipherMethod

/**
 * UI state for the Transcripto screen
 */
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