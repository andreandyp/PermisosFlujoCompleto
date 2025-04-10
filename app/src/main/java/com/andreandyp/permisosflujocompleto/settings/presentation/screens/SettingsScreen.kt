package com.andreandyp.permisosflujocompleto.settings.presentation.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.collectAsEventsWithLifecycle
import com.andreandyp.permisosflujocompleto.settings.presentation.layouts.SettingsLayout
import com.andreandyp.permisosflujocompleto.settings.presentation.state.SettingsEvent
import com.andreandyp.permisosflujocompleto.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onClickChangeUserName: () -> Unit,
    onUpdatedUserName: () -> Unit,
    onClickRestoreData: () -> Unit,
    onClickDeleteData: () -> Unit,
    onAllDataDeleted: () -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.preferences.collectAsStateWithLifecycle()
    viewModel.events.collectAsEventsWithLifecycle {
        when (it) {
            SettingsEvent.UpdatedUserName -> {
                onUpdatedUserName()
                Toast.makeText(
                    context,
                    context.getString(R.string.settings_updated_name),
                    Toast.LENGTH_SHORT,
                ).show()
            }
            SettingsEvent.RestoredData -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.settings_restored_data),
                    Toast.LENGTH_SHORT,
                ).show()
            }
            SettingsEvent.AllDataDeleted -> {
                onAllDataDeleted()
                Toast.makeText(
                    context,
                    context.getString(R.string.settings_all_data_deleted),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    SettingsLayout(
        state = state,
        onTogglePhotoPicker = viewModel::onTogglePhotoPicker,
        onClickChangeUserName = onClickChangeUserName,
        onClickRestoreData = onClickRestoreData,
        onClickDeleteData = onClickDeleteData,
    )
}