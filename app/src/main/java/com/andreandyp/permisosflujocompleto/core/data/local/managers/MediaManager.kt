package com.andreandyp.permisosflujocompleto.core.data.local.managers

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.provider.MediaStore.Video
import android.webkit.MimeTypeMap
import androidx.core.bundle.Bundle
import androidx.core.net.toUri
import com.andreandyp.permisosflujocompleto.core.data.local.models.LocalMedia
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.CAMERA_FILE_PREFIX
import com.andreandyp.permisosflujocompleto.feed.presentation.utils.POST_FILE_PREFIX
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class MediaManager(
    private val ioDispatcher: CoroutineDispatcher,
    private val contentResolver: ContentResolver,
    private val context: Context,
) {
    suspend fun getImages(): List<LocalMedia> =
        withContext(ioDispatcher) {
            val projection = arrayOf(
                Images.Media._ID,
                Images.Media.DISPLAY_NAME,
                Images.Media.SIZE,
                Images.Media.MIME_TYPE,
                Images.Media.DATE_ADDED,
            )

            val collectionUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Images.Media.EXTERNAL_CONTENT_URI
            }

            val images = mutableListOf<LocalMedia>()

            contentResolver
                .query(collectionUri, projection, Bundle().apply {
                    putInt(ContentResolver.QUERY_ARG_LIMIT, 3 * 12)
                    putStringArray(
                        ContentResolver.QUERY_ARG_SORT_COLUMNS,
                        arrayOf(MediaStore.Files.FileColumns.DATE_ADDED)
                    )
                    putInt(
                        ContentResolver.QUERY_ARG_SORT_DIRECTION,
                        ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
                    )
                }, null)
                ?.use { cursor ->
                    val idColumn = cursor.getColumnIndexOrThrow(Images.Media._ID)
                    val displayNameColumn = cursor.getColumnIndexOrThrow(Images.Media.DISPLAY_NAME)
                    val sizeColumn = cursor.getColumnIndexOrThrow(Images.Media.SIZE)
                    val mimeTypeColumn = cursor.getColumnIndexOrThrow(Images.Media.MIME_TYPE)
                    val dateAddedColumn = cursor.getColumnIndexOrThrow(Images.Media.DATE_ADDED)

                    while (cursor.moveToNext()) {
                        val uri =
                            ContentUris.withAppendedId(collectionUri, cursor.getLong(idColumn))
                        val name = cursor.getString(displayNameColumn)
                        val size = cursor.getLong(sizeColumn)
                        val mimeType = cursor.getString(mimeTypeColumn)
                        val dateAdded = cursor.getInt(dateAddedColumn)

                        images.add(
                            LocalMedia(
                                uri = uri,
                                name = name,
                                size = size,
                                mimeType = mimeType,
                                dateAdded = dateAdded,
                            )
                        )
                    }
                }

            return@withContext images
        }

    suspend fun getVideos(): List<LocalMedia> =
        withContext(ioDispatcher) {
            val projection = arrayOf(
                Video.Media._ID,
                Video.Media.DISPLAY_NAME,
                Video.Media.SIZE,
                Video.Media.MIME_TYPE,
                Video.Media.DATE_ADDED,
            )

            val collectionUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                Video.Media.EXTERNAL_CONTENT_URI
            }

            val videos = mutableListOf<LocalMedia>()

            contentResolver
                .query(collectionUri, projection, Bundle().apply {
                    putInt(ContentResolver.QUERY_ARG_LIMIT, 3 * 10)
                    putStringArray(
                        ContentResolver.QUERY_ARG_SORT_COLUMNS,
                        arrayOf(MediaStore.Files.FileColumns.DATE_ADDED)
                    )
                    putInt(
                        ContentResolver.QUERY_ARG_SORT_DIRECTION,
                        ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
                    )
                }, null)
                ?.use { cursor ->
                    val idColumn = cursor.getColumnIndexOrThrow(Video.Media._ID)
                    val displayNameColumn = cursor.getColumnIndexOrThrow(Video.Media.DISPLAY_NAME)
                    val sizeColumn = cursor.getColumnIndexOrThrow(Video.Media.SIZE)
                    val mimeTypeColumn = cursor.getColumnIndexOrThrow(Video.Media.MIME_TYPE)
                    val dateAddedColumn = cursor.getColumnIndexOrThrow(Video.Media.DATE_ADDED)

                    while (cursor.moveToNext()) {
                        val uri =
                            ContentUris.withAppendedId(collectionUri, cursor.getLong(idColumn))
                        val name = cursor.getString(displayNameColumn)
                        val size = cursor.getLong(sizeColumn)
                        val mimeType = cursor.getString(mimeTypeColumn)
                        val dateAdded = cursor.getInt(dateAddedColumn)

                        videos.add(
                            LocalMedia(
                                uri = uri,
                                name = name,
                                size = size,
                                mimeType = mimeType,
                                dateAdded = dateAdded,
                            )
                        )
                    }
                }

            return@withContext videos
        }

    fun copyToLocalStorage(mediaPath: String): String {
        val type = contentResolver.getType(mediaPath.toUri())
        val ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(type)
        val fileName = mediaPath.substringAfterLast("/")
        val targetPath = if (fileName.substringAfterLast(".").isEmpty()) {
            "${context.filesDir}/$POST_FILE_PREFIX-$fileName"
        } else {
            "${context.filesDir}/$POST_FILE_PREFIX-$fileName.$ext"
        }
        val targetFile = File(targetPath)
        contentResolver.openInputStream(mediaPath.toUri()).use {
            Files.copy(
                it,
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING,
            )
        }
        removeCachedFiles()
        return targetFile.toUri().toString()
    }

    fun removeCachedFiles() {
        context.cacheDir.listFiles()?.filter {
            it.name.contains(CAMERA_FILE_PREFIX)
        }?.forEach {
            it.delete()
        }
    }

    fun removeAllMediaPost() {
        context.filesDir.listFiles()?.filter {
            it.name.contains(POST_FILE_PREFIX)
        }?.forEach {
            it.delete()
        }
    }
}