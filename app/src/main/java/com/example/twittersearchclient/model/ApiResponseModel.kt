package com.example.twittersearchclient.model

import com.google.gson.annotations.SerializedName

data class ApiResponseModel(
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("data")
    var tweetList: List<Tweet>
)