package com.tinytap.model.database_entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tinytap.core.constants.DatabaseConstants.REDDIT_BEST_TABLE

@Entity(tableName = REDDIT_BEST_TABLE)
data class BestFromRedditEntity(
    @PrimaryKey
    val id : String,
    val thumbnailUrl: String,
    val title: String,
    val author: String,
    var isCurrentPostOfInterest : Boolean = false
)
