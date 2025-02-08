package com.andreandyp.permisosflujocompleto.feed.presentation.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andreandyp.permisosflujocompleto.feed.presentation.screens.FeedScreen
import com.andreandyp.permisosflujocompleto.feed.presentation.screens.NewPostScreen
import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.FeedViewModel
import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.NewPostViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun FeedSupportingPane(
    navigator: ThreePaneScaffoldNavigator<Nothing>,
    showFeedFab: Boolean,
    showNewPostBack: Boolean,
    feedViewModel: FeedViewModel,
    modifier: Modifier = Modifier,
) {
    SupportingPaneScaffold(
        modifier = modifier,
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        mainPane = {
            AnimatedPane {
                FeedScreen(
                    viewModel = feedViewModel,
                    showFeedFab = showFeedFab,
                    onClickAddPhoto = { navigator.navigateTo(SupportingPaneScaffoldRole.Supporting) },
                    onClickAddTextPost = { navigator.navigateTo(SupportingPaneScaffoldRole.Supporting) },
                )
            }
        },
        supportingPane = {
            AnimatedPane {
                val viewModel = koinViewModel<NewPostViewModel>()
                NewPostScreen(
                    showBackButton = showNewPostBack,
                    viewModel = viewModel,
                    onBack = { navigator.navigateTo(SupportingPaneScaffoldRole.Main) },
                )
            }
        }
    )
}