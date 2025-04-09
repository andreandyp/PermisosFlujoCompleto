package com.andreandyp.permisosflujocompleto.core.presentation.activities

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.andreandyp.permisosflujocompleto.core.domain.models.AllowedMediaPost
import com.andreandyp.permisosflujocompleto.core.presentation.navigation.AppDestinations
import com.andreandyp.permisosflujocompleto.core.presentation.navigation.PreAppDestinations
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme
import com.andreandyp.permisosflujocompleto.feed.presentation.components.FeedNavigationBar
import com.andreandyp.permisosflujocompleto.feed.presentation.components.FeedNavigationDrawer
import com.andreandyp.permisosflujocompleto.feed.presentation.components.FeedNavigationRail
import com.andreandyp.permisosflujocompleto.feed.presentation.dialogs.AskForPermissionsDialog
import com.andreandyp.permisosflujocompleto.feed.presentation.dialogs.DeniedPermissionDialog
import com.andreandyp.permisosflujocompleto.feed.presentation.dialogs.DeniedPermissionRationaleDialog
import com.andreandyp.permisosflujocompleto.feed.presentation.navigation.FeedSupportingPane
import com.andreandyp.permisosflujocompleto.feed.presentation.screens.StartScreen
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.androidMediaPermissions
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.hasPartialAccessMediaPermission
import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.FeedViewModel
import com.andreandyp.permisosflujocompleto.feed.presentation.viewmodels.StartViewModel
import com.andreandyp.permisosflujocompleto.settings.presentation.navigation.SettingsDestinations
import com.andreandyp.permisosflujocompleto.settings.presentation.navigation.settingsNavigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PermisosFlujoCompletoTheme {
                Surface {
                    val appNavController = rememberNavController()
                    NavHost(appNavController, startDestination = PreAppDestinations.Start) {
                        composable<PreAppDestinations.Start> {
                            val viewModel = koinViewModel<StartViewModel>()
                            StartScreen(
                                viewModel = viewModel,
                                onClickContinue = {
                                    appNavController.navigate(PreAppDestinations.Main) {
                                        popUpTo(PreAppDestinations.Start) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }
                        composable<PreAppDestinations.Main> {
                            AppNavigation(
                                onClickAddPhoto = {
                                    appNavController.navigate(
                                        PreAppDestinations.AskForPermissions(AllowedMediaPost.PHOTO.toString())
                                    )
                                },
                                onClickAddVisualMedia = {
                                    appNavController.navigate(
                                        PreAppDestinations.AskForPermissions(AllowedMediaPost.MEDIA.toString())
                                    )
                                },
                            )
                        }
                        dialog<PreAppDestinations.AskForPermissions> { backStackEntry ->
                            val route =
                                backStackEntry.toRoute<PreAppDestinations.AskForPermissions>()
                            AskForPermissionsDialog(
                                allowedMediaPost = AllowedMediaPost.valueOf(route.mediaPost),
                                onGrantedCameraPermission = appNavController::popBackStack,
                                onGrantedMediaPermission = appNavController::popBackStack,
                                onDeniedPermission = { shouldShowRationale ->
                                    if (shouldShowRationale) {
                                        appNavController.navigate(
                                            PreAppDestinations.DeniedPermissionsRationale(
                                                route.mediaPost,
                                            )
                                        ) {
                                            popUpTo(PreAppDestinations.Main)
                                        }
                                    } else {
                                        appNavController.navigate(PreAppDestinations.DeniedPermissions) {
                                            popUpTo(PreAppDestinations.Main)
                                        }
                                    }
                                },
                                onDismiss = appNavController::popBackStack,
                            )
                        }
                        dialog<PreAppDestinations.DeniedPermissionsRationale> { backStackEntry ->
                            val route =
                                backStackEntry.toRoute<PreAppDestinations.DeniedPermissionsRationale>()
                            DeniedPermissionRationaleDialog(
                                onDismiss = appNavController::popBackStack,
                                onGrantedCameraPermission = appNavController::popBackStack,
                                onGrantedMediaPermission = appNavController::popBackStack,
                                allowedMediaPost = AllowedMediaPost.valueOf(route.mediaPost),
                                onDeniedPermission = {
                                    appNavController.navigate(PreAppDestinations.DeniedPermissions) {
                                        popUpTo(PreAppDestinations.Main)
                                    }
                                }
                            )
                        }
                        dialog<PreAppDestinations.DeniedPermissions> {
                            DeniedPermissionDialog(
                                onCancel = appNavController::popBackStack,
                                onAccept = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalPermissionsApi::class)
@Composable
private fun AppNavigation(
    onClickAddPhoto: () -> Unit,
    onClickAddVisualMedia: () -> Unit,
) {
    val navControllerSettings = rememberNavController()
    val navigator = rememberSupportingPaneScaffoldNavigator<AllowedMediaPost?>()
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.FEED) }

    val adaptiveInfo = currentWindowAdaptiveInfo()
    val windowSize = with(LocalDensity.current) {
        currentWindowSize().toSize().toDpSize()
    }
    val navLayoutType = calculateNavLayoutType(adaptiveInfo, windowSize)
    val isMainPaneVisible =
        navigator.scaffoldValue[SupportingPaneScaffoldRole.Main] == PaneAdaptedValue.Expanded
    val isSupportingPaneVisible =
        navigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Expanded
    val showRailFab = isMainPaneVisible || currentDestination == AppDestinations.SETTINGS
    val showFeedFab =
        !isSupportingPaneVisible && navLayoutType != NavigationSuiteType.NavigationRail

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    val feedViewModel = koinViewModel<FeedViewModel>()
    val context = LocalContext.current
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
    val mediaPermissions = rememberMultiplePermissionsState(androidMediaPermissions)

    NavigationSuiteScaffoldLayout(
        layoutType = navLayoutType,
        navigationSuite = {
            FeedNavigationSuite(
                navLayoutType = navLayoutType,
                currentDestination = currentDestination,
                showRailFab = showRailFab,
                hasCameraPermission = cameraPermission.status.isGranted,
                hasMediaPermission = mediaPermissions.allPermissionsGranted || mediaPermissions.hasPartialAccessMediaPermission,
                onClickNewPost = {
                    currentDestination = AppDestinations.FEED
                    navigator.navigateTo(SupportingPaneScaffoldRole.Supporting)
                },
                onClickAddVisualMedia = {
                    currentDestination = AppDestinations.FEED
                    Toast.makeText(context, "Ask for media permissions", Toast.LENGTH_SHORT).show()
                },
                onClickAddPhoto = {
                    currentDestination = AppDestinations.FEED
                    Toast.makeText(context, "Ask for camera permission", Toast.LENGTH_SHORT).show()
                },
                onClickItem = { currentDestination = it },
            )
        }
    ) {
        when (currentDestination) {
            AppDestinations.FEED -> {
                FeedSupportingPane(
                    navigator = navigator,
                    showFeedFab = showFeedFab,
                    showNewPostBack = navigator.canNavigateBack(),
                    feedViewModel = feedViewModel,
                )
            }

            AppDestinations.SETTINGS -> {
                NavHost(
                    navController = navControllerSettings,
                    startDestination = SettingsDestinations.Settings,
                ) {
                    settingsNavigation(navControllerSettings)
                }
            }
        }
    }
}

@Composable
private fun FeedNavigationSuite(
    navLayoutType: NavigationSuiteType,
    currentDestination: AppDestinations,
    showRailFab: Boolean,
    hasCameraPermission: Boolean,
    hasMediaPermission: Boolean,
    onClickNewPost: () -> Unit,
    onClickAddVisualMedia: () -> Unit,
    onClickAddPhoto: () -> Unit,
    onClickItem: (AppDestinations) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (navLayoutType) {
        NavigationSuiteType.NavigationDrawer -> {
            FeedNavigationDrawer(
                showFab = currentDestination == AppDestinations.SETTINGS,
                currentDestination = currentDestination,
                onClickFab = onClickNewPost,
                onClickItem = onClickItem,
                modifier = modifier
            )
        }

        NavigationSuiteType.NavigationBar -> {
            FeedNavigationBar(
                currentDestination = currentDestination,
                onClickItem = onClickItem,
                modifier = modifier
            )
        }

        NavigationSuiteType.NavigationRail -> {
            FeedNavigationRail(
                showFab = showRailFab,
                hasCameraPermission = hasCameraPermission,
                hasMediaPermission = hasMediaPermission,
                currentDestination = currentDestination,
                onClickNewPost = onClickNewPost,
                onClickAddVisualMedia = onClickAddVisualMedia,
                onClickAddPhoto = onClickAddPhoto,
                onClickItem = onClickItem,
                modifier = modifier,
            )
        }
    }
}

private fun calculateNavLayoutType(
    adaptiveInfo: WindowAdaptiveInfo,
    windowSize: DpSize,
): NavigationSuiteType {
    val width = adaptiveInfo.windowSizeClass.windowWidthSizeClass
    val height = adaptiveInfo.windowSizeClass.windowHeightSizeClass

    // The following code comments are assumptions that actually shouldn't be made.
    // They are here just to understand better how to display UI.
    return when {
        // Portrait Phone && Foldable
        width == WindowWidthSizeClass.COMPACT -> NavigationSuiteType.NavigationBar
        // Foldable open in "Nintendo DS" mode
        adaptiveInfo.windowPosture.isTabletop -> NavigationSuiteType.NavigationBar
        // Foldable in "Nintendo DS" mode
        width == WindowWidthSizeClass.MEDIUM && height == WindowHeightSizeClass.MEDIUM -> NavigationSuiteType.NavigationRail
        // Tablet in landscape mode
        width == WindowWidthSizeClass.EXPANDED && windowSize.width >= 1200.dp -> NavigationSuiteType.NavigationDrawer
        // Every other mode
        else -> NavigationSuiteType.NavigationRail
    }
}
