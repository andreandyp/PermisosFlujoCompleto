package com.andreandyp.permisosflujocompleto.feed.presentation.screens

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andreandyp.permisosflujocompleto.core.collectAsEventsWithLifecycle
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.feed.presentation.layouts.NewPostLayout
import com.andreandyp.permisosflujocompleto.feed.presentation.state.NewPostEvents
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.androidMediaPermissions
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.getPhotoUri
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.hasPartialAccessMediaPermission
import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.NewPostViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewPostScreen(
    allowedMediaPost: AllowedMediaPost?,
    showBackButton: Boolean,
    viewModel: NewPostViewModel,
    onBack: () -> Unit,
    onRequirePermission: (AllowedMediaPost) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    viewModel.events.collectAsEventsWithLifecycle {
        when (it) {
            NewPostEvents.GoBack -> onBack()
        }
    }

    val context = LocalContext.current
    var photoUri = remember<Uri?> { null }
    val takePhoto = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            viewModel.onNewMedia(photoUri.toString(), AllowedMediaPost.PHOTO)
        }
    }

    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
    val mediaPermissions = rememberMultiplePermissionsState(androidMediaPermissions)
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    LaunchedEffect(allowedMediaPost) {
        when (allowedMediaPost) {
            AllowedMediaPost.PHOTO -> {
                photoUri = context.getPhotoUri().also(takePhoto::launch)
            }

            AllowedMediaPost.MEDIA -> {
                scope.launch {
                    viewModel.onAddMedia()
                    showBottomSheet = true
                    bottomSheetState.show()
                }
            }

            null -> {}
        }
    }

    NewPostLayout(
        showBackButton = showBackButton,
        hasCameraPermission = cameraPermission.status.isGranted,
        hasFullMediaPermission = mediaPermissions.allPermissionsGranted,
        hasPartialMediaPermission = mediaPermissions.hasPartialAccessMediaPermission,
        showBottomSheet = showBottomSheet,
        bottomSheetState = bottomSheetState,
        state = state,
        onBack = onBack,
        onClickAddPhoto = {
            Toast.makeText(context, "Ask for camera permission", Toast.LENGTH_SHORT).show()
        },
        onClickAddVisualMedia = {
            Toast.makeText(context, "Ask for media permissions", Toast.LENGTH_SHORT).show()
        },
        onChangePostDescription = viewModel::onChangePostDescription,
        onClickUpload = viewModel::onUploadPost,
        onClickRetry = {
            when (it) {
                AllowedMediaPost.PHOTO -> {
                    Toast.makeText(context, "Ask for camera permission", Toast.LENGTH_SHORT).show()
                }

                AllowedMediaPost.MEDIA -> {
                    Toast.makeText(context, "Ask for media permissions", Toast.LENGTH_SHORT).show()
                }
            }
        },
        onDismissGallery = {
            scope.launch {
                bottomSheetState.hide()
                showBottomSheet = false
            }
        },
        onClickPartialAccessButton = {
            scope.launch {
                bottomSheetState.hide()
                showBottomSheet = false
                Toast.makeText(context, "Ask for full media permission", Toast.LENGTH_SHORT).show()
            }
        },
        onClickMedia = {
            scope.launch {
                bottomSheetState.hide()
                showBottomSheet = false
                viewModel.onNewMedia(it.uri, AllowedMediaPost.MEDIA)
            }
        }
    )
}