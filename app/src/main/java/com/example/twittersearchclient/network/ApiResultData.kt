package com.example.twittersearchclient.network

sealed class ApiResultData<out T> {
    data class Success<out T>(val data: T? = null): ApiResultData<T>()
    data class Loading(val nothing: Nothing? = null): ApiResultData<Nothing>()
    data class Failed(val status: String? = null, val message: String? = null): ApiResultData<Nothing>()
    data class Exception(val exception: kotlin.Exception? = null): ApiResultData<Nothing>()
}