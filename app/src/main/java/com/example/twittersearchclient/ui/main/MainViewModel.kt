package com.example.twittersearchclient.ui.main

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.twittersearchclient.data.DataRepository
import com.example.twittersearchclient.data.network.ApiResultData
import com.example.twittersearchclient.model.ApiResponseModel
import com.example.twittersearchclient.model.Tweet
import com.example.twittersearchclient.utils.GeneralUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
    application: Application,
    private val dataRepository: DataRepository
) : AndroidViewModel(application) {

    private var apiNetworkState = MutableLiveData<ApiResultData<String>>()

    fun getInternetConnectionStatus(): Boolean{
        return GeneralUtility.checkInternet(getApplication())
    }

    fun callApiToFetchTweets(){
        apiNetworkState.value = ApiResultData.Loading()
        viewModelScope.launch {
            val apiResponseModel = dataRepository.getAllTweetsFromApi()
            if (apiResponseModel.success){
                insertAllTweets(apiResponseModel.tweetList)
                apiNetworkState.value = ApiResultData.Success("Successful")
            } else {
                apiNetworkState.value = ApiResultData.Failed("failed to get data")
            }
        }
    }

    fun insertAllTweets(tweets: List<Tweet>){
        viewModelScope.launch {
            dataRepository.insertTweetsToDb(tweets)
        }
    }

    fun getAllTweetsFromDb(): LiveData<List<Tweet>>{
        return dataRepository.getAllTweetsFromDb().asLiveData()
    }

    fun getAllFilteredTweetsFromDb(searchedString: String): LiveData<List<Tweet>>{
        return dataRepository.getFilteredTweetFromDb(searchedString).asLiveData()
    }
}