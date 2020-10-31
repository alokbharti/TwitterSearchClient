package com.example.twittersearchclient.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.twittersearchclient.model.Tweet
import kotlinx.coroutines.flow.Flow

@Dao
interface TweetsDao {
    @Insert
    fun insertTweet(tweet: List<Tweet>)

    @Query("SELECT * FROM Tweet")
    fun getAllTweets(): Flow<List<Tweet>>

    @Query("SELECT * FROM Tweet WHERE handle LIKE :searchedText OR name LIKE :searchedText OR text LIKE :searchedText")
    fun getFilteredTweets(searchedText: String): Flow<List<Tweet>>
}