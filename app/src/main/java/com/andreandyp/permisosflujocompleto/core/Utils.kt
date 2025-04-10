package com.andreandyp.permisosflujocompleto.core

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.andreandyp.permisosflujocompleto.core.domain.models.Post
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime

val initialData = listOf(
    Post(
        id = 1L,
        user = "André",
        description = "¡Bienvenido a ésta app de Permisos!",
        mediaPath = null,
        likes = 999,
        creationDate = OffsetDateTime.now(),
    ),
    Post(
        id = 2L,
        user = "André",
        description = "Este es un post de prueba. Prueba a añadir una publicación.",
        mediaPath = null,
        likes = 0,
        creationDate = OffsetDateTime.now(),
    )
)

const val ANDROID_TIMEOUT = 5000L

/**
 * Usage:
 * ```
 * viewModel.events.collectAsEventsWithLifecycle {
 *     when (it) {
 *         // Handle every event
 *     }
 * }
 * ```
 */
@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectAsEventsWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: suspend (T) -> Unit,
) {
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(minActiveState) {
            this@collectAsEventsWithLifecycle.collect(collector)
        }
    }
}