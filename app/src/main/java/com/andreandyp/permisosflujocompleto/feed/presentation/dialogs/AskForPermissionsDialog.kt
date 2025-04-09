package com.andreandyp.permisosflujocompleto.feed.presentation.dialogs

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.feed.presentation.layouts.AskForPermissionsLayoutDialog
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.androidMediaPermissions
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.hasMediaPermission

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

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isPermissionGranted ->
        if (isPermissionGranted) {
            onGrantedCameraPermission()
        } else {
            Toast.makeText(activity, "Ask for rationale", Toast.LENGTH_SHORT).show()
        }
    }
    val mediaPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (hasMediaPermission(permissions)) {
            onGrantedMediaPermission()
        } else {
            Toast.makeText(activity, "Ask for rationale", Toast.LENGTH_SHORT).show()
        }
    }

    AskForPermissionsLayoutDialog(
        allowedMediaPost = allowedMediaPost,
        onConfirm = {
            when (allowedMediaPost) {
                AllowedMediaPost.PHOTO -> cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                AllowedMediaPost.MEDIA -> mediaPermissionLauncher.launch(androidMediaPermissions.toTypedArray())
            }
        },
        onCancel = onDismiss,
        modifier = modifier
    )
}