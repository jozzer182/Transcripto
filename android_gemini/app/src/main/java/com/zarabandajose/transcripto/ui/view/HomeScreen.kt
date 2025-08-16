package com.zarabandajose.transcripto.ui.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zarabandajose.transcripto.R
import com.zarabandajose.transcripto.domain.CipherMethodType
import com.zarabandajose.transcripto.ui.viewmodel.Mode
import com.zarabandajose.transcripto.ui.viewmodel.TranscriptoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: TranscriptoViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.inputText,
                        onValueChange = viewModel::onInputChanged,
                        label = { Text(stringResource(id = R.string.input_text)) },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )

                    SegmentedControl(
                        options = listOf(stringResource(id = R.string.encrypt), stringResource(id = R.string.decrypt)),
                        selectedOption = if (uiState.mode == Mode.ENCRYPT) stringResource(id = R.string.encrypt) else stringResource(id = R.string.decrypt),
                        onOptionSelected = {
                            viewModel.onModeChanged(if (it == context.getString(R.string.encrypt)) Mode.ENCRYPT else Mode.DECRYPT)
                        }
                    )

                    SegmentedControl(
                        options = listOf(stringResource(id = R.string.caesar), stringResource(id = R.string.base64), stringResource(id = R.string.vigenere), stringResource(id = R.string.xor)),
                        selectedOption = when (uiState.method) {
                            CipherMethodType.Caesar -> stringResource(id = R.string.caesar)
                            CipherMethodType.Base64 -> stringResource(id = R.string.base64)
                            CipherMethodType.Vigenere -> stringResource(id = R.string.vigenere)
                            CipherMethodType.XOR -> stringResource(id = R.string.xor)
                        },
                        onOptionSelected = {
                            val method = when (it) {
                                context.getString(R.string.caesar) -> CipherMethodType.Caesar
                                context.getString(R.string.base64) -> CipherMethodType.Base64
                                context.getString(R.string.vigenere) -> CipherMethodType.Vigenere
                                else -> CipherMethodType.XOR
                            }
                            viewModel.onMethodChanged(method)
                        }
                    )

                    AnimatedVisibility(visible = uiState.keyNeedsValidation) {
                        Column {
                            OutlinedTextField(
                                value = uiState.key,
                                onValueChange = viewModel::onKeyChanged,
                                label = { Text(stringResource(id = R.string.key)) },
                                modifier = Modifier.fillMaxWidth(),
                                isError = !uiState.isKeyValid,
                                supportingText = {
                                    if (!uiState.isKeyValid) {
                                        Text(
                                            text = when (uiState.method) {
                                                CipherMethodType.Caesar -> stringResource(id = R.string.key_validation_caesar)
                                                CipherMethodType.Vigenere -> stringResource(id = R.string.key_validation_vigenere)
                                                CipherMethodType.XOR -> stringResource(id = R.string.key_validation_xor)
                                                else -> ""
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = uiState.useSalt,
                            onCheckedChange = viewModel::onUseSaltChanged
                        )
                        Text(stringResource(id = R.string.use_salt))
                    }

                    AnimatedVisibility(visible = uiState.useSalt) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = uiState.salt,
                                onValueChange = viewModel::onSaltChanged,
                                label = { Text(stringResource(id = R.string.salt)) },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Button(onClick = viewModel::onGenerateSalt) {
                                Text(stringResource(id = R.string.generate_salt))
                            }
                        }
                    }

                    Button(
                        onClick = viewModel::onProcess,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = uiState.isKeyValid && uiState.inputText.isNotEmpty()
                    ) {
                        Text(stringResource(id = R.string.process))
                    }

                    if (uiState.outputText.isNotEmpty()) {
                        OutlinedTextField(
                            value = uiState.outputText,
                            onValueChange = {},
                            label = { Text(stringResource(id = R.string.output_text)) },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("result", uiState.outputText)
                                clipboard.setPrimaryClip(clip)
                            }) {
                                Text(stringResource(id = R.string.copy))
                            }
                            Button(onClick = {
                                val textToShare = if (uiState.outputEnvelope.isNotEmpty()) {
                                    uiState.outputEnvelope
                                } else {
                                    uiState.outputText
                                }
                                val sendIntent: Intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, textToShare)
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, null)
                                context.startActivity(shareIntent)
                            }) {
                                Text(stringResource(id = R.string.share))
                            }
                        }
                    }

                    Button(
                        onClick = viewModel::onClear,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(stringResource(id = R.string.clear))
                    }
                }
            }
        }
    }
}

@Composable
fun SegmentedControl(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        options.forEach { option ->
            val selected = selectedOption == option
            Button(
                onClick = { onOptionSelected(option) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                    contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(option)
            }
        }
    }
}
