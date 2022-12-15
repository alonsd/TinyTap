package com.tinytap.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tinytap.data.source.local.dao.RedditDao
import com.tinytap.model.database_entities.BestFromRedditEntity


@Database(
    entities = [BestFromRedditEntity::class],
    version = 1, exportSchema = false
)
abstract class RedditDatabase : RoomDatabase() {


    abstract fun getRedditDao(): RedditDao


}