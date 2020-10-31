package com.example.twittersearchclient.di

import android.content.Context
import androidx.room.Room
import com.example.twittersearchclient.data.db.AppDatabase
import com.example.twittersearchclient.data.db.TweetsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "tweets.db"
        ).build()
    }

    @Provides
    fun provideBlockedContactDao(database: AppDatabase): TweetsDao {
        return database.getTweetsDao()
    }
}