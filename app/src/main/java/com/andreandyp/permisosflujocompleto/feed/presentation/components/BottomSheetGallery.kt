package com.andreandyp.permisosflujocompleto.feed.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PermMedia
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SheetState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.crossfade
import coil3.video.VideoFrameDecoder
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.domain.models.Media
import com.andreandyp.permisosflujocompleto.core.domain.models.MediaType
import com.andreandyp.permisosflujocompleto.feed.presentation.state.NewPostState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetGallery(
    hasPartialMediaPermission: Boolean,
    state: NewPostState,
    bottomSheetState: SheetState,
    onDismiss: () -> Unit,
    onClickPartialAccessButton: () -> Unit,
    onClickMedia: (Media) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(initialPage = 0) { 3 }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState,
        modifier = modifier.statusBarsPadding()
    ) {
        if (hasPartialMediaPermission) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(all = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.has_partial_access),
                    modifier = Modifier.weight(1f)
                )
                OutlinedButton(
                    onClick = onClickPartialAccessButton,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(R.string.has_partial_access_add_more))
                }
            }
        }
        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            MediaTab(pagerState = pagerState, index = 0, icon = Icons.Outlined.PermMedia)
            MediaTab(pagerState = pagerState, index = 1, icon = Icons.Outlined.PhotoLibrary)
            MediaTab(pagerState = pagerState, index = 2, icon = Icons.Outlined.VideoLibrary)
        }

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> MediaList(mediaMap = state.images + state.videos, onClickMedia = onClickMedia)
                1 -> MediaList(mediaMap = state.images, onClickMedia = onClickMedia)
                2 -> MediaList(mediaMap = state.videos, onClickMedia = onClickMedia)
            }
        }
    }
}

@Composable
private fun MediaTab(
    pagerState: PagerState,
    index: Int,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    Tab(
        selected = pagerState.currentPage == index,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
        },
        onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
        modifier = modifier
    )
}

@Composable
private fun MediaList(
    mediaMap: Map<LocalDate, List<Media>>,
    onClickMedia: (Media) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp,
        modifier = modifier
    ) {
        if (mediaMap.isEmpty()) {
            item(span = StaggeredGridItemSpan.FullLine) {
                Text(
                    text = stringResource(R.string.media_picker_empty_label),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 32.dp)
                )
            }
        } else {
            mediaMap.forEach { (date, medias) ->
                item(span = StaggeredGridItemSpan.FullLine) {
                    Text(
                        text = stringResource(
                            R.string.media_picker_date_label,
                            date.format(DateTimeFormatter.ofPattern(stringResource(R.string.media_picker_date_format)))
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                items(medias) { media ->
                    MediaItem(media = media, onClickMedia = onClickMedia)
                }
            }
        }
    }
}

@Composable
private fun MediaItem(
    media: Media,
    onClickMedia: (Media) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (media.mimeType) {
        MediaType.IMAGE -> {
            println("GALLERY: ${media.uri}")
            AsyncImage(
                model = media.uri,
                contentDescription = null,
                modifier = modifier.clickable(onClick = { onClickMedia(media) })
            )
        }

        MediaType.VIDEO -> {
            Box(modifier = modifier.clickable(onClick = { onClickMedia(media) })) {
                Icon(
                    imageVector = Icons.Outlined.PlayCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.Center)
                        .zIndex(0f)
                )
                AsyncImage(
                    model = media.uri,
                    imageLoader = ImageLoader.Builder(LocalContext.current)
                        .components { add(VideoFrameDecoder.Factory()) }
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.zIndex(-1f)
                )
            }
        }
    }
}
