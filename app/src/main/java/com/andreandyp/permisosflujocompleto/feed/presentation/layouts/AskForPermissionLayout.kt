package com.andreandyp.permisosflujocompleto.feed.presentation.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.PermDeviceInformation
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AskForPermissionsLayout(
    allowedMediaPost: AllowedMediaPost,
    onClickRejectPermissions: () -> Unit,
    onClickAcceptPermissions: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.ask_permission_top_bar_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = stringResource(id = R.string.description_new_post_cancel)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
            ) {
                Icon(
                    imageVector = Icons.Filled.PermDeviceInformation,
                    contentDescription = stringResource(id = R.string.ask_permission_title),
                    modifier = Modifier.size(64.dp),
                )
                Text(
                    text = stringResource(id = R.string.ask_permission_title),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )
                AskPermissionText(allowedMediaPost)
                Text(
                    text = stringResource(id = R.string.ask_permission_extra),
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            BottomActions(
                onClickRejectPermissions = onClickRejectPermissions,
                onClickAcceptPermissions = onClickAcceptPermissions,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun AskPermissionText(
    allowedMediaPost: AllowedMediaPost,
    modifier: Modifier = Modifier,
) {
    when (allowedMediaPost) {
        AllowedMediaPost.PHOTO -> {
            Text(
                text = stringResource(id = R.string.ask_permission_message_photos),
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier
            )
        }

        AllowedMediaPost.MEDIA -> {
            Text(
                text = stringResource(id = R.string.ask_permission_message_media),
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun BottomActions(
    onClickRejectPermissions: () -> Unit,
    onClickAcceptPermissions: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextButton(onClick = onClickRejectPermissions, modifier = Modifier.weight(1f)) {
            Text(text = stringResource(id = R.string.ask_permission_reject))
        }
        Button(onClick = onClickAcceptPermissions, modifier = Modifier.weight(1f)) {
            Text(text = stringResource(id = R.string.ask_permission_grant))
        }
    }
}

@PreviewLightDark
@Composable
private fun AskPermissionDialogPhotoPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            AskForPermissionsLayout(
                allowedMediaPost = AllowedMediaPost.PHOTO,
                onClickRejectPermissions = {},
                onClickAcceptPermissions = {},
                onBack = {},
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AskPermissionLayoutMediaPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            AskForPermissionsLayout(
                allowedMediaPost = AllowedMediaPost.MEDIA,
                onClickRejectPermissions = {},
                onClickAcceptPermissions = {},
                onBack = {},
            )
        }
    }
}