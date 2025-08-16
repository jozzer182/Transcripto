package com.zarabandajose.transcripto.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/**
 * Grupo de radio buttons para selección única.
 */
@Composable
fun <T> RadioButtonGroup(
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    optionText: (T) -> String,
    modifier: Modifier = Modifier,
    arrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup()
            .semantics {
                contentDescription = "Grupo de opciones"
            },
        horizontalArrangement = arrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .selectable(
                        selected = (selectedOption == option),
                        onClick = { onOptionSelected(option) },
                        role = Role.RadioButton
                    )
                    .semantics {
                        contentDescription = "Opción: ${optionText(option)}"
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButton(
                    selected = (selectedOption == option),
                    onClick = null // Manejado por el Row
                )
                Text(
                    text = optionText(option),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
