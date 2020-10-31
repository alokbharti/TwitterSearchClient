package com.example.twittersearchclient.di

import com.example.twittersearchclient.data.DataRepository
import com.example.twittersearchclient.data.db.TweetsDao
import com.example.twittersearchclient.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataRepositoryModule {
    @Provides
    fun provideDataRepository(tweetsDao: TweetsDao, apiService: ApiService): DataRepository {
        return DataRepository(tweetsDao, apiService)
    }
}