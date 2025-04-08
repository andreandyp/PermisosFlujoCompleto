package com.andreandyp.permisosflujocompleto.feed.presentation.dialogs

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.feed.presentation.layouts.DeniedPermissionLayoutDialog

@Composable
fun DeniedPermissionRationaleDialog(
    allowedMediaPost: AllowedMediaPost,
    onGrantedCameraPermission: () -> Unit,
    onGrantedMediaPermission: () -> Unit,
    onDeniedPermission: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    DeniedPermissionLayoutDialog(
        shouldShowRationale = true,
        onCancel = onDismiss,
        onAccept = {
            when (allowedMediaPost) {
                AllowedMediaPost.PHOTO -> Toast.makeText(
                    context,
                    "Ask for camera permission",
                    Toast.LENGTH_SHORT
                ).show()

                AllowedMediaPost.MEDIA -> Toast.makeText(
                    context,
                    "Ask for medias permissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
        modifier = modifier
    )
}