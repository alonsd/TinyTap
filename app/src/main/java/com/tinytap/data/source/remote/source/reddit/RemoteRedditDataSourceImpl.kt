package com.tinytap.data.source.remote.source.reddit

import com.tinytap.data.source.remote.api.RedditApi
import javax.inject.Inject

class RemoteRedditDataSourceImpl @Inject constructor(
    private val redditApi: RedditApi,
) : RemoteRedditDataSource {

}


