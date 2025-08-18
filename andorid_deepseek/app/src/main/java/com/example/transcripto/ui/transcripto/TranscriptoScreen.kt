package com.example.transcripto.ui.transcripto

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.transcripto.domain.crypto.CipherMethod

@Composable
fun TranscriptoScreen(
    viewModel: TranscriptoViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Method selection
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CipherMethod.values().forEach { method ->
                FilterChip(
                    selected = state.selectedMethod == method,
                    onClick = { viewModel.onMethodSelected(method) },
                    label = { Text(method.toString()) },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Mode selection
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilterChip(
                selected = state.isEncryptMode,
                onClick = { viewModel.onModeSelected(true) },
                label = { Text("Encrypt") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterChip(
                selected = !state.isEncryptMode,
                onClick = { viewModel.onModeSelected(false) },
                label = { Text("Decrypt") },
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Input field
        OutlinedTextField(
            value = state.inputText,
            onValueChange = viewModel::onInputChanged,
            label = { Text("Input text") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Action button
        Button(
            onClick = viewModel::onProcessClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (state.isEncryptMode) "Encrypt" else "Decrypt")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Output field
        if (state.showResult) {
            OutlinedTextField(
                value = state.outputText,
                onValueChange = {},
                label = { Text("Result") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                maxLines = 5
            )
        }
        
        // Error message
        state.errorMessage?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TranscriptoScreenPreview() {
    TranscriptoScreen()
}