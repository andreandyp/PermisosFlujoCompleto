package com.andreandyp.permisosflujocompleto.feed.presentation.screens

import android.Manifest
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.feed.presentation.layouts.AskForPermissionsLayout
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.androidMediaPermissions
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.hasMediaPermission
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.shouldShowCameraRationale
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.shouldShowMediaPermissionRationale

@Composable
fun AskForPermissionsScreen(
    allowedMediaPost: AllowedMediaPost,
    onClickRejectPermissions: () -> Unit,
    onDeniedPermission: (Boolean) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val activity = requireNotNull(LocalActivity.current)
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isPermissionGranted ->
        if (isPermissionGranted) {
            onBack()
        } else {
            onDeniedPermission(activity.shouldShowCameraRationale())
        }
    }
    val mediaPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (hasMediaPermission(permissions)) {
            onBack()
        } else {
            onDeniedPermission(activity.shouldShowMediaPermissionRationale())
        }
    }

    AskForPermissionsLayout(
        allowedMediaPost = allowedMediaPost,
        onClickRejectPermissions = onClickRejectPermissions,
        onClickAcceptPermissions = {
            when (allowedMediaPost) {
                AllowedMediaPost.PHOTO -> cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                AllowedMediaPost.MEDIA -> mediaPermissionLauncher.launch(androidMediaPermissions.toTypedArray())
            }
        },
        onBack = onBack,
        modifier = modifier
    )
}
