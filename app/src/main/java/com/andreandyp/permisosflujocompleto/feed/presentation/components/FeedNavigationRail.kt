package com.andreandyp.permisosflujocompleto.feed.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.presentation.navigation.AppDestinations
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme

@Composable
fun FeedNavigationRail(
    showFab: Boolean,
    hasCameraPermission: Boolean,
    hasMediaPermission: Boolean,
    currentDestination: AppDestinations,
    onClickNewPost: () -> Unit,
    onClickAddVisualMedia: () -> Unit,
    onClickAddPhoto: () -> Unit,
    onClickItem: (AppDestinations) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            AnimatedVisibility(showFab) {
                Column {
                    ExpandedFab(
                        expandedFabData = listOf(
                            ExpandedFabData(
                                icon = Icons.Default.AddAPhoto,
                                contentDescription = R.string.description_add_photo,
                                isEnabled = hasCameraPermission,
                                onClick = onClickAddPhoto
                            ),
                            ExpandedFabData(
                                icon = Icons.Default.AddPhotoAlternate,
                                contentDescription = R.string.description_add_image,
                                isEnabled = hasMediaPermission,
                                onClick = onClickAddVisualMedia,
                            ),
                            ExpandedFabData(
                                icon = Icons.Default.PostAdd,
                                contentDescription = R.string.description_add_post,
                                isEnabled = true,
                                onClick = onClickNewPost,
                            ),
                        ),
                        reverseLayout = true
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            AppDestinations.entries.forEach { destination ->
                NavigationRailItem(
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = stringResource(destination.label)
                        )
                    },
                    label = { Text(text = stringResource(destination.label)) },
                    selected = currentDestination == destination,
                    onClick = { onClickItem(destination) },
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun FeedNavigationRailPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            FeedNavigationRail(
                showFab = true,
                hasCameraPermission = true,
                hasMediaPermission = true,
                currentDestination = AppDestinations.FEED,
                onClickNewPost = {},
                onClickAddVisualMedia = {},
                onClickAddPhoto = {},
                onClickItem = {},
            )
        }
    }
}