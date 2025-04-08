package com.andreandyp.permisosflujocompleto.feed.presentation.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PermDeviceInformation
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme

@Composable
fun AskForPermissionsLayoutDialog(
    allowedMediaPost: AllowedMediaPost,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onCancel,
        icon = {
            Icon(
                imageVector = Icons.Outlined.PermDeviceInformation,
                contentDescription = null
            )
        },
        title = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = stringResource(R.string.ask_permission_title))
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AskPermissionText(allowedMediaPost = allowedMediaPost)
                Text(
                    text = stringResource(id = R.string.ask_permission_extra),
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = stringResource(android.R.string.cancel))
            }
        },
        modifier = modifier
    )
}

@Composable
private fun AskPermissionText(
    allowedMediaPost: AllowedMediaPost,
    modifier: Modifier = Modifier,
) {
    when (allowedMediaPost) {
        AllowedMediaPost.PHOTO -> {
            Text(
                text = stringResource(id = R.string.ask_permission_message_photos),
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier
            )
        }

        AllowedMediaPost.MEDIA -> {
            Text(
                text = stringResource(id = R.string.ask_permission_message_media),
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AskPermissionLayoutDialogPhotoPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            AskForPermissionsLayoutDialog(
                allowedMediaPost = AllowedMediaPost.PHOTO,
                onCancel = {},
                onConfirm = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AskPermissionLayoutDialogMediaPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            AskForPermissionsLayoutDialog(
                allowedMediaPost = AllowedMediaPost.MEDIA,
                onCancel = {},
                onConfirm = {}
            )
        }
    }
}
