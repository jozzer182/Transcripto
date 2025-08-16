package com.zarabandajose.transcripto_gpt5.ui

import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.zarabandajose.transcripto_gpt5.R
import com.zarabandajose.transcripto_gpt5.TranscriptoViewModel
import com.zarabandajose.transcripto.domain.model.Method

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(vm: TranscriptoViewModel) {
    val state by vm.state.collectAsState()
    val ctx = LocalContext.current

    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(id = R.string.title_transcripto)) })
    }) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Mode chips
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = state.modeEncrypt, onClick = { vm.onToggleMode(true) }, label = { Text(stringResource(R.string.label_encrypt)) })
                FilterChip(selected = !state.modeEncrypt, onClick = { vm.onToggleMode(false) }, label = { Text(stringResource(R.string.label_decrypt)) })
            }

            // Method segmented
            SingleChoiceSegmentedButtonRow {
                Method.entries.forEachIndexed { index, m ->
                    SegmentedButton(selected = state.method == m, onClick = { vm.onMethodChange(m) },
                        shape = SegmentedButtonDefaults.itemShape(index, Method.entries.size),
                        label = { Text(
                            when (m) {
                                Method.BASE64 -> stringResource(R.string.method_base64)
                                Method.CAESAR -> stringResource(R.string.method_caesar)
                                Method.VIGENERE -> stringResource(R.string.method_vigenere)
                                Method.XOR -> stringResource(R.string.method_xor)
                            }
                        ) }
                    )
                }
            }

            OutlinedTextField(
                value = state.input,
                onValueChange = vm::onInputChange,
                label = { Text(stringResource(R.string.label_input_text)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                minLines = 3
            )

            // Key / Shift (hide key for Base64)
            if (state.method == Method.CAESAR) {
                OutlinedTextField(
                    value = state.shift,
                    onValueChange = vm::onShiftChange,
                    label = { Text(stringResource(R.string.label_shift)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            } else if (state.method == Method.VIGENERE || state.method == Method.XOR) {
                OutlinedTextField(
                    value = state.key,
                    onValueChange = vm::onKeyChange,
                    label = { Text(stringResource(R.string.label_key)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Salt controls
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Switch(checked = state.useSalt, onCheckedChange = vm::onUseSaltChange)
                Text(stringResource(R.string.label_use_salt))
            }
            if (state.useSalt) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    AssistChip(onClick = { vm.onAutoSaltChange(true) }, label = { Text(stringResource(R.string.label_auto_salt)) }, enabled = !state.autoSalt)
                    AssistChip(onClick = { vm.onAutoSaltChange(false) }, label = { Text(stringResource(R.string.label_manual_salt)) }, enabled = state.autoSalt)
                }
                if (!state.autoSalt) {
                    OutlinedTextField(
                        value = state.manualSalt,
                        onValueChange = vm::onManualSaltChange,
                        label = { Text(stringResource(R.string.label_use_salt)) },
                        supportingText = { Text("UTF-8") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (state.error != null) {
                Text(state.error!!, color = MaterialTheme.colorScheme.error)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = vm::onProcess) { Text(stringResource(R.string.btn_process)) }
                OutlinedButton(onClick = vm::onClear) { Text(stringResource(R.string.btn_clear)) }
            }

            // Output
            ElevatedCard(Modifier.fillMaxWidth().animateContentSize()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(stringResource(R.string.label_result))
                    Text(state.output)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = vm::onCopy) { Text(stringResource(R.string.btn_copy)) }
                        OutlinedButton(onClick = {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, state.output)
                            }
                            ctx.startActivity(Intent.createChooser(intent, ctx.getString(R.string.share_title)))
                        }) { Text(stringResource(R.string.btn_share)) }
                    }
                }
            }
        }
    }
}
