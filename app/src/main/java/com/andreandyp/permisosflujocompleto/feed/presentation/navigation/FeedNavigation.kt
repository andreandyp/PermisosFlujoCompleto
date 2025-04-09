package com.andreandyp.permisosflujocompleto.feed.presentation.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.feed.presentation.dialogs.DeniedPermissionDialog
import com.andreandyp.permisosflujocompleto.feed.presentation.dialogs.DeniedPermissionRationaleDialog
import com.andreandyp.permisosflujocompleto.feed.presentation.screens.AskForPermissionsScreen
import com.andreandyp.permisosflujocompleto.feed.presentation.screens.FeedScreen
import com.andreandyp.permisosflujocompleto.feed.presentation.screens.NewPostScreen
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.openAppSettings
import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.FeedViewModel
import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.NewPostViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun FeedSupportingPane(
    navigator: ThreePaneScaffoldNavigator<AllowedMediaPost?>,
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
                    onRequirePermission = { allowedMediaPost ->
                        navigator.navigateTo(
                            SupportingPaneScaffoldRole.Extra,
                            allowedMediaPost,
                        )
                    },
                    onClickAddPhoto = {
                        navigator.navigateTo(
                            SupportingPaneScaffoldRole.Supporting,
                            AllowedMediaPost.PHOTO
                        )
                    },
                    onClickAddVisualMedia = {
                        navigator.navigateTo(
                            SupportingPaneScaffoldRole.Supporting,
                            AllowedMediaPost.MEDIA
                        )
                    },
                    onClickAddTextPost = {
                        navigator.navigateTo(
                            SupportingPaneScaffoldRole.Supporting,
                            null
                        )
                    },
                )
            }
        },
        supportingPane = {
            AnimatedPane {
                val viewModel = koinViewModel<NewPostViewModel>()
                NewPostScreen(
                    allowedMediaPost = navigator.currentDestination?.takeIf {
                        it.pane == SupportingPaneScaffoldRole.Supporting
                    }?.content,
                    showBackButton = showNewPostBack,
                    viewModel = viewModel,
                    onBack = {
                        viewModel.onBack()
                        navigator.navigateBack()
                    },
                    onRequirePermission = { allowedMediaPost ->
                        navigator.navigateTo(
                            SupportingPaneScaffoldRole.Extra,
                            allowedMediaPost,
                        )
                    }
                )
            }
        },
        extraPane = {
            AnimatedPane {
                val extraPaneNavController = rememberNavController()

                LaunchedEffect(Unit) {
                    val allowedMediaPost = navigator.currentDestination?.content
                    if (allowedMediaPost == null) {
                        navigator.navigateBack()
                    }
                }
                NavHost(
                    navController = extraPaneNavController,
                    startDestination = ExtraPaneDestinations.AskForPermissions,
                ) {
                    composable<ExtraPaneDestinations.AskForPermissions> {
                        val allowedMediaPost =
                            navigator.currentDestination?.content ?: AllowedMediaPost.PHOTO
                        AskForPermissionsScreen(
                            allowedMediaPost = allowedMediaPost,
                            onClickRejectPermissions = { navigator.navigateBack() },
                            onBack = { navigator.navigateBack() },
                            onDeniedPermission = { shouldShowRationale ->
                                if (shouldShowRationale) {
                                    extraPaneNavController.navigate(
                                        ExtraPaneDestinations.DeniedPermissionsRationale(
                                            allowedMediaPost.toString()
                                        )
                                    ) {
                                        popUpTo(ExtraPaneDestinations.AskForPermissions)
                                    }
                                } else {
                                    extraPaneNavController.navigate(ExtraPaneDestinations.DeniedPermissions) {
                                        popUpTo(ExtraPaneDestinations.AskForPermissions)
                                    }
                                }
                            },
                        )
                    }

                    dialog<ExtraPaneDestinations.DeniedPermissionsRationale> { navBackStackEntry ->
                        val route =
                            navBackStackEntry.toRoute<ExtraPaneDestinations.DeniedPermissionsRationale>()
                        DeniedPermissionRationaleDialog(
                            allowedMediaPost = AllowedMediaPost.valueOf(route.mediaPost),
                            onGrantedCameraPermission = extraPaneNavController::popBackStack,
                            onGrantedMediaPermission = extraPaneNavController::popBackStack,
                            onDismiss = extraPaneNavController::popBackStack,
                            onDeniedPermission = {
                                extraPaneNavController.navigate(ExtraPaneDestinations.DeniedPermissions) {
                                    popUpTo(ExtraPaneDestinations.AskForPermissions)
                                }
                            }
                        )
                    }
                    dialog<ExtraPaneDestinations.DeniedPermissions> {
                        val activity = requireNotNull(LocalActivity.current)
                        DeniedPermissionDialog(
                            onCancel = {
                                extraPaneNavController.popBackStack()
                                navigator.navigateBack()
                            },
                            onAccept = {
                                extraPaneNavController.popBackStack()
                                navigator.navigateBack()
                                activity.openAppSettings()
                            }
                        )
                    }
                }
            }
        }
    )
}