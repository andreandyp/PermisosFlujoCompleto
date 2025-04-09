package com.andreandyp.permisosflujocompleto.feed.presentation.dialogs

import android.Manifest
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.feed.presentation.layouts.AskForPermissionsLayoutDialog
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.androidMediaPermissions
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.hasMediaPermission
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.shouldShowMediaPermissionRationale

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
            onDeniedPermission(
                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.CAMERA,
                )
            )
        }
    }
    val mediaPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (hasMediaPermission(permissions)) {
            onGrantedMediaPermission()
        } else {
            onDeniedPermission(activity.shouldShowMediaPermissionRationale())
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