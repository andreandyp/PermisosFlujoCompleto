package com.andreandyp.permisosflujocompleto.settings.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.andreandyp.permisosflujocompleto.settings.presentation.layouts.DeleteAllDataLayout
import com.andreandyp.permisosflujocompleto.settings.presentation.layouts.UpdateNameLayout
import com.andreandyp.permisosflujocompleto.settings.presentation.screens.SettingsScreen
import com.andreandyp.permisosflujocompleto.settings.presentation.viewmodel.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.settingsNavigation(navController: NavHostController) {
    composable<SettingsDestinations.Settings> {
        val viewModel = koinViewModel<SettingsViewModel>()
        SettingsScreen(
            viewModel = viewModel,
            onClickChangeUserName = { navController.navigate(SettingsDestinations.UpdateName) },
            onUpdatedUserName = navController::popBackStack,
            onClickRestoreData = viewModel::onRestoreData,
            onClickDeleteData = { navController.navigate(SettingsDestinations.DeleteAllData) },
            onAllDataDeleted = navController::popBackStack,
        )
    }
    dialog<SettingsDestinations.UpdateName> { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry<SettingsDestinations.Settings>()
        }
        val viewModel = koinViewModel<SettingsViewModel>(viewModelStoreOwner = parentEntry)
        val state by viewModel.preferences.collectAsStateWithLifecycle()
        UpdateNameLayout(
            currentName = state.userName,
            onCancel = navController::popBackStack,
            onConfirm = viewModel::updateUserName,
        )
    }
    dialog<SettingsDestinations.DeleteAllData> { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry<SettingsDestinations.Settings>()
        }
        val viewModel = koinViewModel<SettingsViewModel>(viewModelStoreOwner = parentEntry)
        DeleteAllDataLayout(
            onCancel = navController::popBackStack,
            onConfirm = viewModel::deleteAllData,
        )
    }
}