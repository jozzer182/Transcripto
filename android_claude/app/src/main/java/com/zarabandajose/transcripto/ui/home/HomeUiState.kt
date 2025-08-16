package com.zarabandajose.transcripto.ui.home

import com.zarabandajose.transcripto.domain.model.CipherMethod

/**
 * Estado de la pantalla principal de Transcripto.
 */
data class HomeUiState(
    val inputText: String = "",
    val outputText: String = "",
    val selectedMethod: CipherMethod = CipherMethod.CAESAR,
    val isEncryptMode: Boolean = true,
    val key: String = "",
    val shift: String = "3",
    val useSalt: Boolean = false,
    val autoGenerateSalt: Boolean = true,
    val manualSalt: String = "",
    val isProcessing: Boolean = false,
    val statusMessage: String = "",
    val isError: Boolean = false,
    val showStatus: Boolean = false
) {
    /**
     * Verifica si la configuración actual es válida para procesar.
     */
    val isValidConfiguration: Boolean
        get() = when {
            inputText.isBlank() -> false
            selectedMethod == CipherMethod.CAESAR -> shift.toIntOrNull() != null && shift.toInt() in -25..25
            selectedMethod in listOf(CipherMethod.VIGENERE, CipherMethod.XOR) -> key.isNotBlank() && key.any { it.isLetter() }
            selectedMethod == CipherMethod.BASE64 -> true
            else -> false
        }
    
    /**
     * Obtiene el texto del botón principal basado en el modo.
     */
    val processButtonText: String
        get() = if (isEncryptMode) "Cifrar" else "Descifrar"
    
    /**
     * Verifica si se debe mostrar el campo de clave.
     */
    val shouldShowKeyField: Boolean
        get() = selectedMethod in listOf(CipherMethod.VIGENERE, CipherMethod.XOR)
    
    /**
     * Verifica si se debe mostrar el campo de desplazamiento.
     */
    val shouldShowShiftField: Boolean
        get() = selectedMethod == CipherMethod.CAESAR
}
