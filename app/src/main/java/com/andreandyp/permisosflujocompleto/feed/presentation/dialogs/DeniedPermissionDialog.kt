package com.andreandyp.permisosflujocompleto.feed.presentation.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andreandyp.permisosflujocompleto.feed.presentation.layouts.DeniedPermissionLayoutDialog

@Composable
fun DeniedPermissionDialog(
    onCancel: () -> Unit,
    onAccept: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DeniedPermissionLayoutDialog(
        shouldShowRationale = false,
        onCancel = onCancel,
        onAccept = onAccept,
        modifier = modifier
    )
}