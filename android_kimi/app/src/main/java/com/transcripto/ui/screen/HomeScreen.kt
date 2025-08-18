package com.transcripto.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getByName
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.transcripto.domain.cipher.CipherMethod
import com.transcripto.R
import com.transcripto.ui.viewmodel.TranscriptoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TranscriptoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    LaunchedEffect(uiState.inputText) {
        if (uiState.inputText.isNotBlank()) {
            viewModel.detectEnvelopeFormat()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transcripto") },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Input Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Input Text",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    OutlinedTextField(
                        value = uiState.inputText,
                        onValueChange = viewModel::onInputTextChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(stringResource(R.string.input_text_hint)) },
                        maxLines = 5,
                        singleLine = false,
                        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.edit_icon_desc)) },
                        keyboardOptions = KeyboardOptions.Default
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Method Selection
                    Text(
                        text = "Cipher Method",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    var expanded by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = uiState.selectedMethod.name,
                            onValueChange = { },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            trailingIcon = { 
                                Icon(
                                    Icons.Default.ArrowDropDown, 
                                    contentDescription = "Select method",
                                    modifier = Modifier.clickable { expanded = !expanded }
                                ) 
                            },
                            label = { Text(stringResource(R.string.method_hint)) }
                        )
                        
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            CipherMethod.values().forEach { method ->
                                DropdownMenuItem(
                                    text = { Text(method.name) },
                                    onClick = { 
                                        viewModel.onSelectedMethodChange(method)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Key Input
                    OutlinedTextField(
                        value = uiState.key,
                        onValueChange = viewModel::onKeyChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(stringResource(R.string.key_hint)) },
                        leadingIcon = { Icon(Icons.Default.VpnKey, contentDescription = stringResource(R.string.key_icon_desc)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Salt Option
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = uiState.useSalt,
                            onCheckedChange = viewModel::onUseSaltChanged,
                            modifier = Modifier.semantics {
                                contentDescription = stringResource(R.string.salt_switch_description)
                            }
                        )
                        Text(stringResource(R.string.use_salt))
                    }

                    // Envelope Detection Notice
                    if (uiState.isEnvelopeDetected) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.envelope_detected),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                uiState.detectedMethod?.let {
                                    Text(
                                        text = "Method: ${it.name}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                                uiState.detectedSalt?.let {
                                    Text(
                                        text = "Salt: ${it.take(8)}...",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = viewModel::encryptText,
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.isLoading
                ) {
                    Icon(Icons.Default.Lock, contentDescription = stringResource(R.string.encrypt_icon_desc))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.encrypt_button))
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Button(
                    onClick = viewModel::decryptText,
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.isLoading
                ) {
                    Icon(Icons.Default.LockOpen, contentDescription = stringResource(R.string.decrypt_icon_desc))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.decrypt_button))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(
                    onClick = viewModel::clearAll,
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.isLoading
                ) {
                    Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.clear_icon_desc))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.clear_button))
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                OutlinedButton(
                    onClick = viewModel::copyOutputToInput,
                    modifier = Modifier.weight(1f),
                    enabled = uiState.outputText.isNotEmpty() && !uiState.isLoading
                ) {
                    Icon(Icons.Default.ArrowForward, contentDescription = stringResource(R.string.use_output_icon_desc))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.use_output_button))
                }
            }

            // Error Message
            uiState.errorMessage?.let { error ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Output Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Output",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    if (uiState.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        OutlinedTextField(
                            value = uiState.outputText,
                            onValueChange = { },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Result") },
                            readOnly = true,
                            maxLines = 5,
                            singleLine = false
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IconButton(
                                onClick = {
                                    if (uiState.outputText.isNotEmpty()) {
                                        clipboardManager.setText(AnnotatedString(uiState.outputText))
                                    }
                                },
                                enabled = uiState.outputText.isNotEmpty()
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = stringResource(R.string.copy_output_button))
                            }
                            
                            IconButton(
                                onClick = {
                                    if (uiState.outputText.isNotEmpty()) {
                                        val shareIntent = android.content.Intent().apply {
                                            action = android.content.Intent.ACTION_SEND
                                            type = "text/plain"
                                            putExtra(android.content.Intent.EXTRA_TEXT, uiState.outputText)
                                        }
                                        context.startActivity(
                                            android.content.Intent.createChooser(
                                                shareIntent,
                                                stringResource(R.string.share_result_title)
                                            )
                                        )
                                    }
                                },
                                enabled = uiState.outputText.isNotEmpty()
                            ) {
                                Icon(Icons.Default.Share, contentDescription = stringResource(R.string.share_output_button))
                            }
                        }
                    }
                }
            }
        }
    }
}