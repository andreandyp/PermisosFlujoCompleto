package com.andreandyp.permisosflujocompleto.feed.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.domain.models.Post
import com.andreandyp.permisosflujocompleto.core.initialData
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme
import java.time.format.DateTimeFormatter

@Composable
fun PostItem(
    post: Post,
    onClickLike: (Post) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(modifier = modifier) {
        ListItem(
            headlineContent = { Text(text = post.user) },
            overlineContent = {
                Text(
                    text = post.creationDate
                        .toLocalDateTime()
                        .format(
                            DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm")
                        )
                )
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(id = R.string.description_profile_picture),
                    Modifier.size(72.dp)
                )
            },
            supportingContent = {
                Column {
                    Text(text = post.description)
                    if (LocalInspectionMode.current) {
                        Image(
                            painter = painterResource(R.drawable.demo_full_icon),
                            contentDescription = stringResource(id = R.string.description_post_media_feed),
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        AsyncImage(
                            model = post.mediaPath,
                            contentDescription = stringResource(id = R.string.description_post_media_feed),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                }
            },
            trailingContent = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(onClick = { onClickLike(post) }) {
                        Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "")
                    }
                    Text(text = post.likes.toString())
                }
            }
        )
        HorizontalDivider()
    }
}

@PreviewLightDark
@Composable
fun PostItemPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            PostItem(
                post = initialData[0],
                onClickLike = {},
            )
        }
    }
}