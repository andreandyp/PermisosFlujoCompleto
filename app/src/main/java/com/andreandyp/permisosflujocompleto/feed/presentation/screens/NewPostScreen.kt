package com.andreandyp.permisosflujocompleto.feed.presentation.screens

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
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
import com.andreandyp.permisosflujocompleto.R
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
    val context = LocalContext.current
    var photoUri = remember<Uri?> { null }
    val takePhoto = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            viewModel.onNewMedia(photoUri.toString(), AllowedMediaPost.PHOTO)
        }
    }
    val pickVisualMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            viewModel.onNewMedia(uri.toString(), AllowedMediaPost.MEDIA)
        }
    }
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val state by viewModel.state.collectAsStateWithLifecycle()
    viewModel.events.collectAsEventsWithLifecycle {
        when (it) {
            NewPostEvents.GoBack -> onBack()
            NewPostEvents.LaunchCamera -> {
                photoUri = context.getPhotoUri().also(takePhoto::launch)
            }

            NewPostEvents.ShowAndroidPicker -> callPhotoPicker(pickVisualMedia, context)
            NewPostEvents.ShowAppPicker -> scope.launch {
                viewModel.onAddMedia()
                showBottomSheet = true
                bottomSheetState.show()
            }
        }
    }

    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
    val mediaPermissions = rememberMultiplePermissionsState(androidMediaPermissions)

    LaunchedEffect(Unit) {
        viewModel.onInit(allowedMediaPost)
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
            if (cameraPermission.status.isGranted) {
                photoUri = context.getPhotoUri().also(takePhoto::launch)
            } else {
                onRequirePermission(AllowedMediaPost.PHOTO)
            }
        },
        onClickAddVisualMedia = {
            when {
                state.isPhotoPickerEnabled -> callPhotoPicker(pickVisualMedia, context)
                mediaPermissions.hasPartialAccessMediaPermission -> scope.launch {
                    viewModel.onAddMedia()
                    showBottomSheet = true
                    bottomSheetState.show()
                }

                mediaPermissions.allPermissionsGranted -> scope.launch {
                    viewModel.onAddMedia()
                    showBottomSheet = true
                    bottomSheetState.show()
                }

                else -> onRequirePermission(AllowedMediaPost.MEDIA)
            }
        },
        onChangePostDescription = viewModel::onChangePostDescription,
        onClickUpload = viewModel::onUploadPost,
        onClickRetry = {
            when (it) {
                AllowedMediaPost.PHOTO -> {
                    if (cameraPermission.status.isGranted) {
                        photoUri = context.getPhotoUri().also(takePhoto::launch)
                    } else {
                        onRequirePermission(AllowedMediaPost.PHOTO)
                    }
                }

                AllowedMediaPost.MEDIA -> {
                    if (mediaPermissions.allPermissionsGranted) {
                        scope.launch {
                            viewModel.onAddMedia()
                            showBottomSheet = true
                            bottomSheetState.show()
                        }
                    } else {
                        onRequirePermission(AllowedMediaPost.MEDIA)
                    }
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
                mediaPermissions.launchMultiplePermissionRequest()
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

private fun callPhotoPicker(
    pickVisualMedia: ActivityResultLauncher<PickVisualMediaRequest>,
    context: Context,
) {
    try {
        pickVisualMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageAndVideo))
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(
            context,
            context.getString(R.string.photo_picker_exception),
            Toast.LENGTH_SHORT,
        ).show()
    }
}