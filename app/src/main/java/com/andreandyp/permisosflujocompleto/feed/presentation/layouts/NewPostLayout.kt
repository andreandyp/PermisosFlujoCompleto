package com.andreandyp.permisosflujocompleto.feed.presentation.layouts

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme
import com.andreandyp.permisosflujocompleto.feed.presentation.state.NewPostState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPostLayout(
    showBackButton: Boolean,
    state: NewPostState,
    onBack: () -> Unit,
    onClickNewPhoto: () -> Unit,
    onClickNewImage: () -> Unit,
    onClickNewVideo: () -> Unit,
    onChangePostDescription: (String) -> Unit,
    onClickUpload: () -> Unit,
    onClickRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.description_new_post)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        if (showBackButton) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.description_new_post_cancel)
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(id = R.string.description_profile_picture),
                    modifier = Modifier.size(72.dp)
                )
                Text(text = state.userName, modifier = Modifier.fillMaxWidth())
            }
            OutlinedTextField(
                value = state.postDescription,
                onValueChange = onChangePostDescription,
                label = { Text(text = stringResource(id = R.string.label_post)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            if (state.mediaUri != null) {
                if (LocalInspectionMode.current) {
                    Image(
                        painter = painterResource(R.drawable.demo_full_icon),
                        contentDescription = stringResource(id = R.string.description_profile_picture),
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    AsyncImage(
                        model = state.mediaUri,
                        contentDescription = stringResource(id = R.string.description_post_media),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (state.newMedia == null) {
                    Text(text = stringResource(id = R.string.add_media_post))
                } else {
                    Text(text = stringResource(id = R.string.replace_media_post))
                }

                when (state.newMedia) {
                    AllowedMediaPost.PHOTO -> {
                        NewImage(onClick = onClickNewImage)
                        NewVideo(onClick = onClickNewVideo)
                    }

                    AllowedMediaPost.IMAGE -> {
                        NewPhoto(onClick = onClickNewPhoto)
                        NewVideo(onClick = onClickNewVideo)
                    }

                    AllowedMediaPost.VIDEO -> {
                        NewPhoto(onClick = onClickNewPhoto)
                        NewImage(onClick = onClickNewImage)
                    }

                    null -> {
                        NewPhoto(onClick = onClickNewPhoto)
                        NewImage(onClick = onClickNewImage)
                        NewVideo(onClick = onClickNewVideo)
                    }
                }
            }

            Button(
                onClick = onClickUpload,
                enabled = state.postDescription.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Publish,
                    contentDescription = stringResource(id = R.string.upload_post),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text = stringResource(id = R.string.upload_post))
            }
            when (state.newMedia) {
                AllowedMediaPost.PHOTO -> {
                    RetryButton(textId = R.string.retry_photo_post, onClickRetry = onClickRetry)
                }

                AllowedMediaPost.IMAGE -> {
                    RetryButton(textId = R.string.retry_image_post, onClickRetry = onClickRetry)
                }

                AllowedMediaPost.VIDEO -> {
                    RetryButton(textId = R.string.retry_video_post, onClickRetry = onClickRetry)
                }

                null -> {}

            }
        }
    }
}

@Composable
private fun NewPhoto(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.AddAPhoto,
            contentDescription = stringResource(id = R.string.description_add_photo),
        )
    }
}

@Composable
private fun NewImage(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.AddPhotoAlternate,
            contentDescription = stringResource(R.string.description_add_image),
        )
    }
}

@Composable
private fun NewVideo(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.VideoCall,
            contentDescription = stringResource(R.string.description_add_video),
        )
    }
}

@Composable
private fun RetryButton(
    @StringRes textId: Int,
    onClickRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClickRetry,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Outlined.RestartAlt,
            contentDescription = stringResource(id = textId),
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(text = stringResource(id = textId))
    }
}

@PreviewLightDark
@Composable
private fun NewTextPostLayoutPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            NewPostLayout(
                showBackButton = true,
                state = NewPostState(userName = "André", postDescription = "Hola"),
                onBack = {},
                onClickNewPhoto = {},
                onClickNewImage = {},
                onClickNewVideo = {},
                onChangePostDescription = {},
                onClickUpload = {},
                onClickRetry = {},
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun NewMediaPostLayoutPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            NewPostLayout(
                showBackButton = true,
                state = NewPostState(
                    userName = "André",
                    mediaUri = "",
                    newMedia = AllowedMediaPost.PHOTO
                ),
                onBack = {},
                onClickNewPhoto = {},
                onClickNewImage = {},
                onClickNewVideo = {},
                onChangePostDescription = {},
                onClickUpload = {},
                onClickRetry = {},
            )
        }
    }
}