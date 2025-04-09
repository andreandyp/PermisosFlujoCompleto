package com.andreandyp.permisosflujocompleto.feed.presentation.dialogs

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.feed.presentation.layouts.DeniedPermissionLayoutDialog
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.androidMediaPermissions
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.hasMediaPermission

@Composable
fun DeniedPermissionRationaleDialog(
    allowedMediaPost: AllowedMediaPost,
    onGrantedCameraPermission: () -> Unit,
    onGrantedMediaPermission: () -> Unit,
    onDeniedPermission: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isPermissionGranted ->
        if (isPermissionGranted) {
            onGrantedCameraPermission()
        } else {
            onDeniedPermission()
        }
    }
    val mediaPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (hasMediaPermission(permissions)) {
            onGrantedMediaPermission()
        } else {
            onDeniedPermission()
        }
    }

    DeniedPermissionLayoutDialog(
        shouldShowRationale = true,
        onCancel = onDismiss,
        onAccept = {
            when (allowedMediaPost) {
                AllowedMediaPost.PHOTO -> cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                AllowedMediaPost.MEDIA -> mediaPermissionLauncher.launch(androidMediaPermissions.toTypedArray())
            }
        },
        modifier = modifier
    )
}