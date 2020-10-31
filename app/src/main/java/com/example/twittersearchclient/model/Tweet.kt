package com.example.twittersearchclient.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Tweet (
    @SerializedName("name")
    var name: String,
    @SerializedName("handle")
    var handle: String,
    @SerializedName("profileImageUrl")
    var profileImageUrl: String,
    @SerializedName("retweetCount")
    var retweetCount: Int,
    @SerializedName("favoriteCount")
    var favoriteCount: Int,
    @SerializedName("text")
    var text: String,
    @PrimaryKey(autoGenerate = true)
    var timeStamp: Long
)