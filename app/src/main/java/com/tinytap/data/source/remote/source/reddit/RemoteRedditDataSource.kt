package com.tinytap.data.source.remote.source.reddit

import com.haroldadmin.cnradapter.NetworkResponse
import com.tinytap.model.network_models.RedditResponseModel
import kotlinx.coroutines.flow.Flow

interface RemoteRedditDataSource {

    val bestFromReddit : Flow<NetworkResponse<RedditResponseModel, String>>
}