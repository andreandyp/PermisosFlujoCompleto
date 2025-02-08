package com.andreandyp.permisosflujocompleto.feed.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andreandyp.permisosflujocompleto.core.collectAsEventsWithLifecycle
import com.andreandyp.permisosflujocompleto.feed.presentation.layouts.NewPostLayout
import com.andreandyp.permisosflujocompleto.feed.presentation.state.NewPostEvents
import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.NewPostViewModel

@Composable
fun NewPostScreen(
    viewModel: NewPostViewModel,
    showBackButton: Boolean,
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    viewModel.events.collectAsEventsWithLifecycle {
        when (it) {
            NewPostEvents.GoBack -> onBack()
        }
    }

    NewPostLayout(
        showBackButton = showBackButton,
        state = state,
        onBack = onBack,
        onClickNewPhoto = {},
        onClickNewImage = {},
        onClickNewVideo = {},
        onChangePostDescription = viewModel::onChangePostDescription,
        onClickUpload = viewModel::onUploadPost,
        onClickRetry = {},
    )
}