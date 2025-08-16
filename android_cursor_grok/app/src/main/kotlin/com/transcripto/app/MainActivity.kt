package com.transcripto.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.transcripto.ui.theme.TranscriptoTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.transcripto.R
import com.transcripto.domain.Method

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: TranscriptoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranscriptoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(viewModel = viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: TranscriptoViewModel, modifier: Modifier = Modifier) {
    val state = viewModel.state.collectAsState().value
    // val haptics = LocalHapticFeedback.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Transcripto") })
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Modo
                    SingleChoiceSegmentedButtonRow {
                        SegmentedButton(
                            selected = state.mode == Mode.ENCRYPT,
                            onClick = { viewModel.onModeChanged(Mode.ENCRYPT) },
                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                        ) {
                            Text(stringResource(R.string.encrypt))
                        }
                        SegmentedButton(
                            selected = state.mode == Mode.DECRYPT,
                            onClick = { viewModel.onModeChanged(Mode.DECRYPT) },
                            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                        ) {
                            Text(stringResource(R.string.decrypt))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Método
                    TabRow(selectedTabIndex = Method.values().indexOf(state.method)) {
                        Method.values().forEachIndexed { index, method ->
                            Tab(
                                selected = state.method == method,
                                onClick = { viewModel.onMethodChanged(method) },
                                text = { Text(method.name) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input
                    OutlinedTextField(
                        value = state.input,
                        onValueChange = viewModel::onInputChanged,
                        label = { Text(stringResource(R.string.input_text)) },
                        modifier = Modifier.fillMaxWidth(),
                        isError = state.input.isEmpty() && state.error != null
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Campos condicionales
                    AnimatedVisibility(visible = state.method == Method.CAESAR) {
                        OutlinedTextField(
                            value = state.shift.toString(),
                            onValueChange = { viewModel.onShiftChanged(it.toIntOrNull() ?: 0) },
                            label = { Text(stringResource(R.string.shift)) },
                            modifier = Modifier.fillMaxWidth(),
                            isError = state.method == Method.CAESAR && state.shift == 0 && state.error != null
                        )
                    }

                    AnimatedVisibility(visible = state.method == Method.VIGENERE || state.method == Method.XOR) {
                        OutlinedTextField(
                            value = state.key,
                            onValueChange = viewModel::onKeyChanged,
                            label = { Text(stringResource(R.string.key)) },
                            modifier = Modifier.fillMaxWidth(),
                            isError = (state.method == Method.VIGENERE || state.method == Method.XOR) && state.key.isEmpty() && state.error != null
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Salt
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = state.useSalt,
                            onCheckedChange = viewModel::onUseSaltChanged
                        )
                        Text(stringResource(R.string.use_salt))
                    }

                    AnimatedVisibility(visible = state.useSalt) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = state.generateAuto,
                                    onClick = { viewModel.onGenerateAutoChanged(true) }
                                )
                                Text(stringResource(R.string.generate_auto))
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = !state.generateAuto,
                                    onClick = { viewModel.onGenerateAutoChanged(false) }
                                )
                                Text(stringResource(R.string.enter_manual))
                            }
                            if (!state.generateAuto) {
                                OutlinedTextField(
                                    value = state.salt,
                                    onValueChange = viewModel::onSaltChanged,
                                    label = { Text(stringResource(R.string.salt)) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón Procesar
                    Button(
                        onClick = {
                            // haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.onProcess()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.process))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Output
                    if (state.output.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.output_text),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(viewModel.getDisplayedOutput())
                    }

                    if (state.error != null) {
                        Text(
                            text = state.error?.let { stringResource(R.string.error, it) } ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botones Copiar, Compartir, Limpiar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            // haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.onCopy()
                        }) {
                            Text(stringResource(R.string.copy))
                        }
                        Button(onClick = {
                            // haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.onShare()
                        }) {
                            Text(stringResource(R.string.share))
                        }
                        Button(onClick = {
                            // haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.onClear()
                        }) {
                            Text(stringResource(R.string.clear))
                        }
                    }
                }
            }
        }
    }
}
