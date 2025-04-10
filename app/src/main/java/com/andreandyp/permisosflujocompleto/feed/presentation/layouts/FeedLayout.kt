package com.andreandyp.permisosflujocompleto.feed.presentation.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.domain.models.Post
import com.andreandyp.permisosflujocompleto.core.initialData
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme
import com.andreandyp.permisosflujocompleto.feed.presentation.components.ExpandedFab
import com.andreandyp.permisosflujocompleto.feed.presentation.components.ExpandedFabData
import com.andreandyp.permisosflujocompleto.feed.presentation.components.PostItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedLayout(
    posts: List<Post>,
    showFeedFab: Boolean,
    hasCameraPermission: Boolean,
    hasMediaPermission: Boolean,
    onClickLike: (Post) -> Unit,
    onClickAddPhoto: () -> Unit,
    onClickAddMedia: () -> Unit,
    onClickAddTextPost: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            if (showFeedFab) {
                ExpandedFab(
                    listOf(
                        ExpandedFabData(
                            icon = Icons.Default.AddAPhoto,
                            contentDescription = R.string.description_add_photo,
                            isEnabled = hasCameraPermission,
                            onClick = onClickAddPhoto,
                        ),
                        ExpandedFabData(
                            icon = Icons.Default.AddPhotoAlternate,
                            contentDescription = R.string.description_add_image,
                            isEnabled = hasMediaPermission,
                            onClick = onClickAddMedia,
                        ),
                        ExpandedFabData(
                            icon = Icons.Default.PostAdd,
                            contentDescription = R.string.description_add_post,
                            isEnabled = true,
                            onClick = onClickAddTextPost,
                        ),
                    ),
                    reverseLayout = false,
                )
            }
        },
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        if (posts.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.feed_empty),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyVerticalGrid(
                contentPadding = paddingValues,
                columns = GridCells.Adaptive(minSize = 350.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp),
            ) {
                items(posts) {
                    PostItem(post = it, onClickLike = onClickLike)
                }
            }
        }
    }
}

@PreviewScreenSizes
@PreviewLightDark
@Composable
private fun FeedLayoutPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            FeedLayout(
                posts = initialData,
                showFeedFab = true,
                hasCameraPermission = true,
                hasMediaPermission = true,
                onClickLike = {},
                onClickAddPhoto = {},
                onClickAddMedia = {},
                onClickAddTextPost = {},
            )
        }
    }
}