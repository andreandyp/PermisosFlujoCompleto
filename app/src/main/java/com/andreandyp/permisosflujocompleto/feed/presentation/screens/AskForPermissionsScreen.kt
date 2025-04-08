package com.andreandyp.permisosflujocompleto.feed.presentation.screens

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.feed.presentation.layouts.AskForPermissionsLayout

@Composable
fun AskForPermissionsScreen(
    allowedMediaPost: AllowedMediaPost,
    onClickRejectPermissions: () -> Unit,
    onDeniedPermission: (Boolean) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val activity = requireNotNull(LocalActivity.current)

    AskForPermissionsLayout(
        allowedMediaPost = allowedMediaPost,
        onClickRejectPermissions = onClickRejectPermissions,
        onClickAcceptPermissions = {
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
        onBack = onBack,
        modifier = modifier
    )
}
