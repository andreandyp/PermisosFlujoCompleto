package com.andreandyp.permisosflujocompleto.settings.presentation.layouts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme

@Composable
fun UpdateNameLayout(
    currentName: String,
    onCancel: () -> Unit,
    onConfirm: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var name by remember { mutableStateOf(currentName) }
    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = { onConfirm(name) }) {
                Text(text = stringResource(id = R.string.settings_update_name_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = stringResource(id = android.R.string.cancel))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.settings_update_name_title))
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
        },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = stringResource(id = R.string.start_text_field)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
private fun UpdateNameLayoutPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            UpdateNameLayout(
                currentName = "André",
                onCancel = {},
                onConfirm = {},
            )
        }
    }
}