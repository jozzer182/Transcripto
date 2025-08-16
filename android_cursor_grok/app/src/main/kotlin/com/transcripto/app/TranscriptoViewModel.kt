package com.transcripto.app

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.transcripto.domain.DecryptTextUseCase
import com.transcripto.domain.EncryptTextUseCase
import com.transcripto.domain.GenerateSaltUseCase
import com.transcripto.domain.Method
import com.transcripto.domain.DetectAndParseEnvelopeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TranscriptoState(
    val input: String = "",
    val output: String = "",
    val method: Method = Method.CAESAR,
    val mode: Mode = Mode.ENCRYPT,
    val key: String = "",
    val shift: Int = 0,
    val useSalt: Boolean = false,
    val generateAuto: Boolean = true,
    val salt: String = "",
    val error: String? = null
)

@HiltViewModel
class TranscriptoViewModel @Inject constructor(
    private val encryptUseCase: EncryptTextUseCase,
    private val decryptUseCase: DecryptTextUseCase,
    private val generateSaltUseCase: GenerateSaltUseCase,
    private val parser: DetectAndParseEnvelopeUseCase,
    application: Application
) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(TranscriptoState())
    val state = _state.asStateFlow()

    fun onInputChanged(text: String) {
        _state.value = _state.value.copy(input = text)
    }

    fun onMethodChanged(method: Method) {
        _state.value = _state.value.copy(method = method)
    }

    fun onModeChanged(mode: Mode) {
        _state.value = _state.value.copy(mode = mode)
    }

    fun onKeyChanged(key: String) {
        _state.value = _state.value.copy(key = key)
    }

    fun onShiftChanged(shift: Int) {
        _state.value = _state.value.copy(shift = shift)
    }

    fun onUseSaltChanged(use: Boolean) {
        _state.value = _state.value.copy(useSalt = use)
    }

    fun onGenerateAutoChanged(auto: Boolean) {
        _state.value = _state.value.copy(generateAuto = auto)
    }

    fun onSaltChanged(salt: String) {
        _state.value = _state.value.copy(salt = salt)
    }

    fun onProcess() {
        viewModelScope.launch {
            try {
                val currentState = _state.value
                var usedSalt: String? = if (currentState.useSalt) currentState.salt else null
                if (currentState.useSalt && currentState.generateAuto) {
                    usedSalt = generateSaltUseCase()
                }
                val output = if (currentState.mode == Mode.ENCRYPT) {
                    encryptUseCase(currentState.input, currentState.method, currentState.key.takeIf { it.isNotEmpty() }, currentState.shift.takeIf { currentState.method == Method.CAESAR }, usedSalt)
                } else {
                    decryptUseCase(currentState.input, currentState.method, currentState.key.takeIf { it.isNotEmpty() }, currentState.shift.takeIf { currentState.method == Method.CAESAR }, usedSalt)
                }
                _state.value = currentState.copy(output = output, error = null)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun onCopy() {
        val clipboard = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("Transcripto", getDisplayedOutput()))
    }

    fun onShare() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, _state.value.output)
        }
        val chooser = Intent.createChooser(intent, "Compartir con")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ContextCompat.startActivity(getApplication(), chooser, null)
    }

    fun onClear() {
        _state.value = TranscriptoState()
    }

    fun getDisplayedOutput(): String {
        val current = state.value.output
        return if (state.value.mode == Mode.ENCRYPT) {
            parser(current)?.get("payload") as? String ?: current
        } else current
    }
}
