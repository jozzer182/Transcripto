package com.zarabandajose.transcripto.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zarabandajose.transcripto.core.ui.components.RadioButtonGroup
import com.zarabandajose.transcripto.core.ui.components.SaltConfigSection
import com.zarabandajose.transcripto.core.ui.components.StatusCard
import com.zarabandajose.transcripto.domain.model.CipherMethod

/**
 * Pantalla principal de Transcripto.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val hapticFeedback = LocalHapticFeedback.current
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Transcripto",
                        style = MaterialTheme.typography.headlineSmall
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tarjeta principal de contenido
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .animateContentSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Título de la sección
                    Text(
                        text = "Cifrado y Descifrado",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    // Campo de texto de entrada
                    OutlinedTextField(
                        value = uiState.inputText,
                        onValueChange = viewModel::updateInputText,
                        label = { Text("Texto a ${if (uiState.isEncryptMode) "cifrar" else "descifrar"}") },
                        placeholder = { Text("Ingrese el texto aquí") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics {
                                contentDescription = "Campo de texto de entrada"
                            },
                        minLines = 3,
                        maxLines = 6
                    )
                    
                    // Selector de modo (Cifrar/Descifrar)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Modo",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        SingleChoiceSegmentedButtonRow(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                                onClick = { viewModel.updateMode(true) },
                                selected = uiState.isEncryptMode
                            ) {
                                Text("Cifrar")
                            }
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                                onClick = { viewModel.updateMode(false) },
                                selected = !uiState.isEncryptMode
                            ) {
                                Text("Descifrar")
                            }
                        }
                    }
                    
                    // Selector de método
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Método de cifrado",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        RadioButtonGroup(
                            options = CipherMethod.values().toList(),
                            selectedOption = uiState.selectedMethod,
                            onOptionSelected = viewModel::updateSelectedMethod,
                            optionText = { method ->
                                when (method) {
                                    CipherMethod.CAESAR -> "César"
                                    CipherMethod.BASE64 -> "Base64"
                                    CipherMethod.VIGENERE -> "Vigenère"
                                    CipherMethod.XOR -> "XOR"
                                }
                            },
                            arrangement = Arrangement.SpaceEvenly
                        )
                    }
                    
                    // Campos específicos según el método
                    if (uiState.shouldShowKeyField) {
                        OutlinedTextField(
                            value = uiState.key,
                            onValueChange = viewModel::updateKey,
                            label = { Text("Clave") },
                            placeholder = { Text("Ingrese la clave") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics {
                                    contentDescription = "Campo de clave para ${uiState.selectedMethod.name}"
                                },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            isError = uiState.shouldShowKeyField && uiState.key.isBlank()
                        )
                    }
                    
                    if (uiState.shouldShowShiftField) {
                        OutlinedTextField(
                            value = uiState.shift,
                            onValueChange = viewModel::updateShift,
                            label = { Text("Desplazamiento") },
                            placeholder = { Text("Ej: 3") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics {
                                    contentDescription = "Campo de desplazamiento para César"
                                },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = uiState.shift.toIntOrNull() == null || uiState.shift.toIntOrNull()!! !in -25..25
                        )
                    }
                }
            }
            
            // Sección de configuración de salt
            SaltConfigSection(
                useSalt = uiState.useSalt,
                onUseSaltChange = viewModel::updateUseSalt,
                autoGenerate = uiState.autoGenerateSalt,
                onAutoGenerateChange = viewModel::updateAutoGenerateSalt,
                manualSalt = uiState.manualSalt,
                onManualSaltChange = viewModel::updateManualSalt
            )
            
            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        hapticFeedback.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                        viewModel.processText()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .semantics {
                            contentDescription = "Botón para ${uiState.processButtonText.lowercase()}"
                        },
                    enabled = !uiState.isProcessing && uiState.isValidConfiguration
                ) {
                    if (uiState.isProcessing) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                    Text(uiState.processButtonText)
                }
                
                OutlinedButton(
                    onClick = {
                        hapticFeedback.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                        viewModel.clearAll()
                    },
                    modifier = Modifier.semantics {
                        contentDescription = "Botón para limpiar todos los campos"
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text("Limpiar")
                }
            }
            
            // Tarjeta de resultado
            if (uiState.outputText.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Resultado",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        
                        Text(
                            text = uiState.outputText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.semantics {
                                contentDescription = "Texto resultado"
                            }
                        )
                        
                        // Botones de copiar y compartir
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    hapticFeedback.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                    copyToClipboard(context, uiState.outputText)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .semantics {
                                        contentDescription = "Copiar resultado al portapapeles"
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 4.dp)
                                )
                                Text("Copiar")
                            }
                            
                            OutlinedButton(
                                onClick = {
                                    hapticFeedback.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                    shareText(context, uiState.outputText)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .semantics {
                                        contentDescription = "Compartir resultado"
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 4.dp)
                                )
                                Text("Compartir")
                            }
                        }
                    }
                }
            }
            
            // Mensaje de estado
            StatusCard(
                message = uiState.statusMessage,
                isError = uiState.isError,
                isVisible = uiState.showStatus
            )
            
            // Espaciado inferior
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Copia texto al portapapeles.
 */
private fun copyToClipboard(context: Context, text: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Transcripto Result", text)
    clipboardManager.setPrimaryClip(clip)
}

/**
 * Comparte texto usando Android Sharesheet.
 */
private fun shareText(context: Context, text: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        putExtra(Intent.EXTRA_SUBJECT, "Resultado de Transcripto")
        type = "text/plain"
    }
    
    val chooser = Intent.createChooser(shareIntent, "Compartir resultado")
    context.startActivity(chooser)
}
