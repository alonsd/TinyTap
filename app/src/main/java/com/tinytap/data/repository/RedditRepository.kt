package com.tinytap.data.repository

import com.tinytap.model.ui_models.DashboardCardModel
import kotlinx.coroutines.flow.Flow

interface RedditRepository {

    val networkError : Flow<String>

    fun getDashboardModels() : Flow<List<DashboardCardModel>>

    fun deleteEntity(entityId: String)

    suspend fun getNewRedditModels()

    suspend fun updatePostOfInterest(entityId: String,isCurrentlyThePostOfInterest: Boolean)
}