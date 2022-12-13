package com.tinytap.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.tinytap.model.ui_models.DashboardCardModel
import kotlinx.coroutines.flow.Flow

interface RedditRepository {

    val dashboardModels: Flow<NetworkResponse<List<DashboardCardModel>, String>>
}