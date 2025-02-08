package com.andreandyp.permisosflujocompleto.feed.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.andreandyp.permisosflujocompleto.core.presentation.navigation.AppDestinations

@Composable
fun FeedNavigationBar(
    currentDestination: AppDestinations,
    onClickItem: (AppDestinations) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        AppDestinations.entries.forEach { destination ->
            NavigationBarItem(
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