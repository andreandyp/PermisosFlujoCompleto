package com.andreandyp.permisosflujocompleto.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andreandyp.permisosflujocompleto.core.data.local.dao.PostsDao
import com.andreandyp.permisosflujocompleto.core.data.local.entities.PostEntity

@Database(
    entities = [
        PostEntity::class,
    ],
    version = 1,
)
abstract class PermisosDatabase : RoomDatabase() {
    abstract val postsDao: PostsDao
}