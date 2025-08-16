package com.zarabandajose.transcripto.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * Sección de configuración para salt.
 */
@Composable
fun SaltConfigSection(
    useSalt: Boolean,
    onUseSaltChange: (Boolean) -> Unit,
    autoGenerate: Boolean,
    onAutoGenerateChange: (Boolean) -> Unit,
    manualSalt: String,
    onManualSaltChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Configuración de Salt",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            // Toggle usar salt
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "Activar o desactivar uso de salt"
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Usar salt",
                    style = MaterialTheme.typography.bodyLarge
                )
                Switch(
                    checked = useSalt,
                    onCheckedChange = onUseSaltChange
                )
            }
            
            if (useSalt) {
                // Opción de generación automática
                RadioButtonGroup(
                    options = listOf(true, false),
                    selectedOption = autoGenerate,
                    onOptionSelected = onAutoGenerateChange,
                    optionText = { if (it) "Generar automáticamente" else "Ingresar manual" },
                    modifier = Modifier.semantics {
                        contentDescription = "Tipo de generación de salt"
                    }
                )
                
                // Campo manual si no es automático
                if (!autoGenerate) {
                    OutlinedTextField(
                        value = manualSalt,
                        onValueChange = onManualSaltChange,
                        label = { Text("Salt manual") },
                        placeholder = { Text("Ingrese el salt") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics {
                                contentDescription = "Campo para ingresar salt manual"
                            },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
            }
        }
    }
}
