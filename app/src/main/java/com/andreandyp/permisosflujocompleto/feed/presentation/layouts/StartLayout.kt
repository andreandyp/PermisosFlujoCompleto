package com.andreandyp.permisosflujocompleto.feed.presentation.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.andreandyp.permisosflujocompleto.R
import com.andreandyp.permisosflujocompleto.core.presentation.theme.PermisosFlujoCompletoTheme
import com.andreandyp.permisosflujocompleto.feed.presentation.state.StartState

@Composable
fun StartLayout(
    state: StartState,
    onChangeUserName: (String) -> Unit,
    onClickContinue: () -> Unit,
    onCheckOmit: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = stringResource(id = R.string.start_welcome),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Box(modifier = Modifier.widthIn(max = 350.dp)) {
            OutlinedTextField(
                value = state.userName,
                onValueChange = onChangeUserName,
                label = {
                    Text(text = stringResource(id = R.string.start_text_field))
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(
            onClick = onClickContinue,
            enabled = state.userName.isNotBlank(),
            modifier = Modifier.widthIn(max = 350.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Login,
                    contentDescription = stringResource(id = R.string.start_button_continue),
                    modifier = Modifier.align(Alignment.CenterStart),
                )
                Text(
                    text = stringResource(id = R.string.start_button_continue),
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.start_omit_switch))
            Switch(
                checked = state.omitWelcome,
                onCheckedChange = onCheckOmit,
            )
        }
    }
}

@PreviewScreenSizes
@PreviewLightDark
@Composable
fun StartLayoutPreview() {
    PermisosFlujoCompletoTheme {
        Surface {
            StartLayout(
                state = StartState(),
                onChangeUserName = {},
                onClickContinue = {},
                onCheckOmit = {},
            )
        }
    }
}