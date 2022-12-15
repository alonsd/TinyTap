package com.tinytap.di.binding

import com.tinytap.data.source.local.source.RedditLocalDataSource
import com.tinytap.data.source.local.source.RedditLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRedditLocalDataSource(
        redditLocalDataSourceImpl: RedditLocalDataSourceImpl
    ): RedditLocalDataSource
}