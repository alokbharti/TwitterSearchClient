package com.example.twittersearchclient.data.network

import com.example.twittersearchclient.model.ApiResponseModel
import retrofit2.http.GET

interface ApiService {
    @GET("tweets")
    suspend fun getAllTweets(): ApiResponseModel
}