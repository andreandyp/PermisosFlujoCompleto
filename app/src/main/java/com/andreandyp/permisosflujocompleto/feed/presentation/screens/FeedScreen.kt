package com.andreandyp.permisosflujocompleto.feed.presentation.screens

import android.Manifest
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.feed.presentation.layouts.FeedLayout
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.androidMediaPermissions
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.hasPartialAccessMediaPermission
import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.FeedViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    showFeedFab: Boolean,
    onClickAddPhoto: () -> Unit,
    onClickAddVisualMedia: () -> Unit,
    onClickAddTextPost: () -> Unit,
    onRequirePermission: (AllowedMediaPost) -> Unit,
    modifier: Modifier = Modifier,
) {
    val posts by viewModel.posts.collectAsStateWithLifecycle(emptyList())
    val context = LocalContext.current
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
    val mediaPermissions = rememberMultiplePermissionsState(androidMediaPermissions)

    FeedLayout(
        posts = posts,
        showFeedFab = showFeedFab,
        hasCameraPermission = cameraPermission.status.isGranted,
        hasMediaPermission = mediaPermissions.allPermissionsGranted || mediaPermissions.hasPartialAccessMediaPermission,
        onClickLike = viewModel::onClickLike,
        onClickAddPhoto = {
            Toast.makeText(context, "Ask for camera permission", Toast.LENGTH_SHORT).show()
        },
        onClickAddMedia = {
            Toast.makeText(context, "Ask for media permissions", Toast.LENGTH_SHORT).show()
        },
        onClickAddTextPost = onClickAddTextPost,
        modifier = modifier,
    )
}
