package com.example.twittersearchclient.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.twittersearchclient.model.Tweet

@Database(entities = [Tweet::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getTweetsDao() : TweetsDao
}