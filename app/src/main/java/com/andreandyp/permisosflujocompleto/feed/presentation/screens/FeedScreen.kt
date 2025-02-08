package com.andreandyp.permisosflujocompleto.feed.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andreandyp.permisosflujocompleto.feed.presentation.layouts.FeedLayout
import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.FeedViewModel

@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    showFeedFab: Boolean,
    onClickAddPhoto: () -> Unit,
    onClickAddTextPost: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val posts by viewModel.posts.collectAsStateWithLifecycle(emptyList())
    FeedLayout(
        posts = posts,
        showFeedFab = showFeedFab,
        onClickLike = viewModel::onClickLike,
        onClickAddPhoto = onClickAddPhoto,
        onClickAddTextPost = onClickAddTextPost,
        modifier = modifier,
    )
}