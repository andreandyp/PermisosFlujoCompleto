package com.andreandyp.permisosflujocompleto.feed.presentation.dialogs

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.feed.presentation.layouts.AskForPermissionsLayoutDialog

@Composable
fun AskForPermissionsDialog(
    allowedMediaPost: AllowedMediaPost,
    onGrantedCameraPermission: () -> Unit,
    onGrantedMediaPermission: () -> Unit,
    onDeniedPermission: (Boolean) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val activity = requireNotNull(LocalActivity.current)

    AskForPermissionsLayoutDialog(
        allowedMediaPost = allowedMediaPost,
        onConfirm = {
            when (allowedMediaPost) {
                AllowedMediaPost.PHOTO -> Toast.makeText(
                    activity,
                    "Ask for camera permission",
                    Toast.LENGTH_SHORT
                ).show()

                AllowedMediaPost.MEDIA -> Toast.makeText(
                    activity,
                    "Ask for media permissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
        onCancel = onDismiss,
        modifier = modifier
    )
}