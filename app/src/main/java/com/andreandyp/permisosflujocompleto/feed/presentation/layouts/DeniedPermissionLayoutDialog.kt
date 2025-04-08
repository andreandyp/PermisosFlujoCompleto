package com.andreandyp.permisosflujocompleto.feed.presentation.layouts

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PermDeviceInformation
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme

@Composable
fun DeniedPermissionLayoutDialog(
    shouldShowRationale: Boolean,
    onCancel: () -> Unit,
    onAccept: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = onAccept) {
                if (shouldShowRationale) {
                    Text(text = stringResource(R.string.ask_permission_grant))
                } else {
                    Text(text = stringResource(R.string.denied_permission_go_to_settings))
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = stringResource(id = R.string.denied_permission_close))
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Outlined.PermDeviceInformation,
                contentDescription = null
            )
        },
        title = { Text(text = stringResource(id = R.string.denied_permission_title)) },
        text = {
            if (shouldShowRationale) {
                Text(text = stringResource(R.string.denied_permission_message_rationale))
            } else {
                Text(text = stringResource(id = R.string.denied_permission_message))
            }
        },
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
private fun DeniedPermissionLayoutDialogRationalePreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            DeniedPermissionLayoutDialog(
                shouldShowRationale = true,
                onCancel = {},
                onAccept = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun DeniedPermissionLayoutDialogPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            DeniedPermissionLayoutDialog(
                shouldShowRationale = false,
                onCancel = {},
                onAccept = {}
            )
        }
    }
}