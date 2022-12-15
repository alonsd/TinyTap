package com.tinytap.data.repository

import com.tinytap.model.ui_models.DashboardCardModel
import kotlinx.coroutines.flow.Flow

interface RedditRepository {

    fun getDashboardModels() : Flow<List<DashboardCardModel>>

    suspend fun getNewRedditModels()

    fun deleteEntity(entityId: String)
}