package com.andreandyp.permisosflujocompleto.settings.presentation.layouts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Restore
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme
import com.andreandyp.permisosflujocompleto.settings.presentation.state.SettingsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsLayout(
    state: SettingsState,
    onClickChangeUserName: () -> Unit,
    onClickRestoreData: () -> Unit,
    onClickDeleteData: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.settings_title)) },
            )
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.setting_title),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(
                        horizontal = 56.dp,
                        vertical = 8.dp,
                    ),
                )
                UserNameSetting(currentName = state.userName, onClick = onClickChangeUserName)
                RestoreInitialDataSetting(onClick = onClickRestoreData)
            }
            HorizontalDivider(modifier = Modifier.padding(top = 0.dp, bottom = 16.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                DeleteAllDataSetting(onClick = onClickDeleteData)
            }
        }
    }
}

@Composable
private fun UserNameSetting(currentName: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            imageVector = Icons.Rounded.Person,
            contentDescription = stringResource(id = R.string.description_settings_user_name)
        )
        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.settings_user_name_title),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = stringResource(id = R.string.settings_user_name_summary, currentName),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun RestoreInitialDataSetting(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            imageVector = Icons.Rounded.Restore,
            contentDescription = stringResource(id = R.string.description_settings_restore_data),
        )
        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.settings_restore_data_title),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = stringResource(id = R.string.settings_restore_data_summary),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun DeleteAllDataSetting(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            imageVector = Icons.Rounded.DeleteForever,
            contentDescription = stringResource(id = R.string.description_settings_delete_data),
            tint = Color.Red,
        )
        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.settings_delete_data_title),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Red,
            )
            Text(
                text = stringResource(id = R.string.settings_delete_data_summary),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Red,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreferencesLayoutEmptyPreferencesPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            SettingsLayout(
                state = SettingsState(),
                onClickChangeUserName = {},
                onClickRestoreData = {},
                onClickDeleteData = {},
            )
        }
    }
}