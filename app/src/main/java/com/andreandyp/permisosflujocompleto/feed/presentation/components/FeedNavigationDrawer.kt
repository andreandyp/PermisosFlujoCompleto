package com.andreandyp.permisosflujocompleto.feed.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.presentation.navigation.AppDestinations
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme

@Composable
fun FeedNavigationDrawer(
    showFab: Boolean,
    onClickFab: () -> Unit,
    currentDestination: AppDestinations,
    onClickItem: (AppDestinations) -> Unit,
    modifier: Modifier = Modifier,
) {
    PermanentDrawerSheet(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedVisibility(showFab) {
                Column {
                    ExtendedFloatingActionButton(
                        onClick = onClickFab,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.description_new_post),
                        )
                        Text(
                            text = stringResource(id = R.string.description_new_post),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            AppDestinations.entries.forEach { destination ->
                NavigationDrawerItem(
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
private fun FeedNavigationDrawerPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            FeedNavigationDrawer(
                showFab = true,
                onClickFab = {},
                currentDestination = AppDestinations.FEED,
                onClickItem = {},
            )
        }
    }
}