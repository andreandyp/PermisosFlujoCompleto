package com.andreandyp.permisosflujocompleto.feed.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme

private const val DURATION_MS = 100
private const val DELAY_MS = 50

data class ExpandedFabData(
    val icon: ImageVector,
    val contentDescription: Int,
    val isEnabled: Boolean,
    val onClick: () -> Unit,
)

@Composable
fun ExpandedFab(
    expandedFabData: List<ExpandedFabData>,
    modifier: Modifier = Modifier,
    reverseLayout: Boolean,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        if (reverseLayout) {
            MainFab(
                isExpanded = isExpanded,
                onClickMainFab = { isExpanded = !isExpanded },
            )
            SmallExpandableFabs(
                expandedFabData = expandedFabData,
                isExpanded = isExpanded,
                reverse = true,
                onClickSmallFab = { isExpanded = false },
            )
        } else {
            SmallExpandableFabs(
                expandedFabData = expandedFabData,
                isExpanded = isExpanded,
                reverse = false,
                onClickSmallFab = { isExpanded = false },
            )
            MainFab(
                isExpanded = isExpanded,
                onClickMainFab = { isExpanded = !isExpanded },
            )
        }
    }
}

@Composable
private fun MainFab(
    isExpanded: Boolean,
    onClickMainFab: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(onClick = onClickMainFab, modifier = modifier) {
        Crossfade(targetState = isExpanded, label = "ExpandedFabCrossFade") {
            if (it) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = stringResource(id = R.string.description_new_post_cancel),
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = stringResource(id = R.string.description_new_post),
                )
            }
        }
    }
}

@Composable
private fun SmallExpandableFabs(
    expandedFabData: List<ExpandedFabData>,
    isExpanded: Boolean,
    reverse: Boolean,
    onClickSmallFab: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val fabData = if (reverse) {
        expandedFabData.reversed()
    } else {
        expandedFabData
    }
    fabData.forEachIndexed { index, data ->
        val enterDelay = if (reverse) {
            DELAY_MS * (index + 1)
        } else {
            DELAY_MS * (expandedFabData.size - index)
        }
        val exitDelay = if (reverse) {
            DELAY_MS * (expandedFabData.size - index)
        } else {
            DELAY_MS * (index + 1)
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = slideInVertically(
                tween(DURATION_MS, enterDelay)
            ) { it } + fadeIn(tween(DURATION_MS, enterDelay)),
            exit = slideOutVertically(
                tween(DURATION_MS, exitDelay)
            ) { it } + fadeOut(tween(DURATION_MS, exitDelay)),
            modifier = modifier,
        ) {
            if (data.isEnabled.not()) {
                BadgedBox(
                    badge = { Badge(containerColor = MaterialTheme.colorScheme.error) {} },
                ) {
                    SmallFab(data = data, onClickSmallFab = onClickSmallFab)
                }
            } else {
                SmallFab(data = data, onClickSmallFab = onClickSmallFab)
            }
        }
    }
}

@Composable
private fun SmallFab(
    data: ExpandedFabData,
    onClickSmallFab: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SmallFloatingActionButton(
        onClick = {
            onClickSmallFab()
            data.onClick()
        },
        containerColor = if (data.isEnabled) FloatingActionButtonDefaults.containerColor else Color.Gray,
        modifier = modifier
    ) {
        Icon(
            imageVector = data.icon,
            contentDescription = stringResource(id = data.contentDescription),
        )
    }
}

@PreviewLightDark
@Composable
private fun ExpandedFabPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            ExpandedFab(
                expandedFabData = listOf(
                    ExpandedFabData(
                        icon = Icons.Default.AddAPhoto,
                        contentDescription = R.string.description_add_photo,
                        isEnabled = true,
                        onClick = {},
                    ),
                    ExpandedFabData(
                        icon = Icons.Default.AddPhotoAlternate,
                        contentDescription = R.string.description_add_image,
                        isEnabled = false,
                        onClick = {},
                    ),
                ),
                reverseLayout = false,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ExpandedFabReversedPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            ExpandedFab(
                expandedFabData = listOf(
                    ExpandedFabData(
                        icon = Icons.Default.AddAPhoto,
                        contentDescription = R.string.description_add_photo,
                        isEnabled = true,
                        onClick = {},
                    ),
                    ExpandedFabData(
                        icon = Icons.Default.AddPhotoAlternate,
                        contentDescription = R.string.description_add_image,
                        isEnabled = false,
                        onClick = {},
                    ),
                ),
                reverseLayout = true,
            )
        }
    }
}
