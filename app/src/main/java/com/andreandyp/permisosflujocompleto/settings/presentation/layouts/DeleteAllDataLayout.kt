package com.andreandyp.permisosflujocompleto.settings.presentation.layouts

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme
import kotlinx.coroutines.delay

private const val CONFIRM_SECONDS = 3

@Composable
fun DeleteAllDataLayout(
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isConfirmButtonEnabled by remember { mutableStateOf(false) }
    var seconds by remember { mutableIntStateOf(CONFIRM_SECONDS) }
    LaunchedEffect(Unit) {
        for (i in CONFIRM_SECONDS downTo 1) {
            seconds = i
            delay(1000)
        }
        isConfirmButtonEnabled = true
    }
    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = onConfirm, enabled = isConfirmButtonEnabled) {
                Text(
                    text = if (isConfirmButtonEnabled) {
                        stringResource(R.string.settings_delete_all_confirm)
                    } else {
                        stringResource(R.string.settings_delete_all_confirm_counter, seconds)
                    },
                    color = if (isConfirmButtonEnabled) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
                    }
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = stringResource(id = android.R.string.cancel))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.settings_delete_all_title))
        },
        icon = {
            Icon(
                imageVector = Icons.Default.DeleteForever,
                contentDescription = stringResource(id = R.string.settings_delete_all_title),
                tint = MaterialTheme.colorScheme.error
            )
        },
        text = {
            Text(text = stringResource(id = R.string.settings_delete_all_text))
        },
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
private fun DeleteAllDataLayoutPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            DeleteAllDataLayout(
                onCancel = {},
                onConfirm = {},
            )
        }
    }
}