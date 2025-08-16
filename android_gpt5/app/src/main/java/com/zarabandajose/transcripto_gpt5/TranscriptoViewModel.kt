package com.zarabandajose.transcripto_gpt5

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zarabandajose.transcripto.core.common.AppResult
import com.zarabandajose.transcripto.domain.cipher.CipherMethod
import com.zarabandajose.transcripto.domain.model.Method
import com.zarabandajose.transcripto.domain.usecase.DecryptText
import com.zarabandajose.transcripto.domain.usecase.DetectAndParseEnvelope
import com.zarabandajose.transcripto.domain.usecase.EncryptText
import com.zarabandajose.transcripto.domain.usecase.GenerateSalt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Base64
import javax.inject.Inject

@HiltViewModel
class TranscriptoViewModel @Inject constructor(
    private val encryptText: EncryptText,
    private val decryptText: DecryptText,
    private val detectAndParseEnvelope: DetectAndParseEnvelope,
    private val generateSalt: GenerateSalt,
    private val appContext: Context,
) : ViewModel() {

    data class UiState(
        val input: String = "",
        val output: String = "",
        val modeEncrypt: Boolean = true,
        val method: Method = Method.XOR,
        val key: String = "",
        val shift: String = "",
        val useSalt: Boolean = false,
        val autoSalt: Boolean = true,
        val manualSalt: String = "",
        val error: String? = null,
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    fun onInputChange(v: String) = _state.update { it.copy(input = v) }
    fun onKeyChange(v: String) = _state.update { it.copy(key = v) }
    fun onShiftChange(v: String) = _state.update { it.copy(shift = v) }
    fun onMethodChange(m: Method) = _state.update { it.copy(method = m) }
    fun onToggleMode(encrypt: Boolean) = _state.update { it.copy(modeEncrypt = encrypt) }
    fun onUseSaltChange(b: Boolean) = _state.update { it.copy(useSalt = b) }
    fun onAutoSaltChange(b: Boolean) = _state.update { it.copy(autoSalt = b) }
    fun onManualSaltChange(v: String) = _state.update { it.copy(manualSalt = v) }

    fun onClear() = _state.update { UiState(method = it.method) }

    fun onCopy() {
        val cm = appContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Transcripto", state.value.output)
        cm.setPrimaryClip(clip)
    }

    fun onShare() {
        // handled by UI via callback; no-op here
    }

    fun onProcess() {
        viewModelScope.launch {
            val s = state.value
            val saltBytes = if (s.useSalt) {
                if (s.autoSalt) when (val res = generateSalt()) {
                    is AppResult.Success -> res.data
                    is AppResult.Error -> null
                } else s.manualSalt.takeIf { it.isNotEmpty() }?.toByteArray()
            } else null
            val params = CipherMethod.Params(
                key = s.key.takeIf { s.method == Method.VIGENERE || s.method == Method.XOR },
                shift = s.shift.toIntOrNull(),
                salt = saltBytes,
                useSalt = s.useSalt
            )

            val inputText = s.input
            val env = when (val d = detectAndParseEnvelope(inputText)) {
                is AppResult.Success -> d.data
                is AppResult.Error -> null
            }

            val (method, payload, envParams) = if (env != null) {
                Triple(env.method, env.payload, params.copy(salt = env.salt, useSalt = env.salt != null))
            } else Triple(s.method, inputText, params)

            val result = if (s.modeEncrypt) encryptText(method, payload, envParams, includeEnvelope = false)
            else decryptText(method, payload, envParams)

            when (result) {
                is AppResult.Success -> _state.update { it.copy(output = result.data, error = null) }
                is AppResult.Error -> _state.update { it.copy(error = result.message) }
            }
        }
    }
}
