package com.tinytap.di

import com.tinytap.data.repository.RedditRepository
import com.tinytap.data.repository.RedditRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRedditRepository(
        redditRepositoryImpl: RedditRepositoryImpl
    ): RedditRepository
}