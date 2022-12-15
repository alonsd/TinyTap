package com.tinytap.data.source.remote.source.reddit

import com.haroldadmin.cnradapter.NetworkResponse
import com.tinytap.model.network_models.RedditResponseModel

interface RemoteRedditDataSource {

    suspend fun getBestFromReddit()  : NetworkResponse<RedditResponseModel, String>
}