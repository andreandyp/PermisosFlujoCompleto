package com.andreandyp.permisosflujocompleto.core.data.repositories

import com.andreandyp.permisosflujocompleto.core.data.local.managers.MediaManager
import com.andreandyp.permisosflujocompleto.core.domain.models.Media
import com.andreandyp.permisosflujocompleto.core.domain.models.MediaType
import com.andreandyp.permisosflujocompleto.core.domain.models.Post
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class MediaRepository(
    private val mediaManager: MediaManager,
) {
    suspend fun getImages(): Map<LocalDate, List<Media>> {
        return mediaManager.getImages().map {
            val offset: ZoneOffset = ZoneId.systemDefault().rules.getOffset(Instant.now())
            println(it.uri.toString())
            Media(
                uri = it.uri.toString(),
                name = it.name,
                size = it.size,
                mimeType = MediaType.IMAGE,
                dateAdded = LocalDateTime.ofEpochSecond(it.dateAdded.toLong(), 0, offset),
            )
        }.groupBy { it.dateAdded.toLocalDate() }
    }

    suspend fun getVideos(): Map<LocalDate, List<Media>> {
        return mediaManager.getVideos().map {
            val offset: ZoneOffset = ZoneId.systemDefault().rules.getOffset(Instant.now())
            Media(
                uri = it.uri.toString(),
                name = it.name,
                size = it.size,
                mimeType = MediaType.VIDEO,
                dateAdded = LocalDateTime.ofEpochSecond(it.dateAdded.toLong(), 0, offset),
            )
        }.groupBy { it.dateAdded.toLocalDate() }
    }

    fun saveMediaPost(post: Post) {
        post.imagePath?.let {
            mediaManager.copyToLocalStorage(it)
        }
    }
}