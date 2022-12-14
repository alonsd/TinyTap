package com.tinytap.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.tinytap.model.network_models.RedditResponseModel
import com.tinytap.model.ui_models.DashboardCardModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

interface RedditRepository {

    suspend fun getRedditModel() : NetworkResponse<RedditResponseModel, String>
}