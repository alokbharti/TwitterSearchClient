package com.example.twittersearchclient.data

import com.example.twittersearchclient.data.db.TweetsDao
import com.example.twittersearchclient.data.network.ApiService
import com.example.twittersearchclient.model.ApiResponseModel
import com.example.twittersearchclient.model.Tweet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val tweetsDao: TweetsDao,
    private val apiService: ApiService
){
    suspend fun getAllTweetsFromApi(): ApiResponseModel{
        return apiService.getAllTweets()
    }

    suspend fun insertTweetsToDb(tweets: List<Tweet>){
        withContext(Dispatchers.IO){
            tweetsDao.insertTweet(tweets)
        }
    }

    fun getAllTweetsFromDb() : Flow<List<Tweet>>{
        return tweetsDao.getAllTweets()
    }

    fun getFilteredTweetFromDb(searchedString: String): Flow<List<Tweet>>{
        return tweetsDao.getFilteredTweets(searchedString)
    }

}