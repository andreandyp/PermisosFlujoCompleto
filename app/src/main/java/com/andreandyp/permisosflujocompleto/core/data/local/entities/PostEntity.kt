package com.andreandyp.permisosflujocompleto.core.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val user: String,
    val description: String,
    @ColumnInfo(name = "image_path")
    val imagePath: String?,
    val likes: Int,
    val creationDate: String,
)