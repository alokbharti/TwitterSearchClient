package com.example.twittersearchclient.ui.main

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.twittersearchclient.data.DataRepository
import com.example.twittersearchclient.data.network.ApiResultData
import com.example.twittersearchclient.model.ApiResponseModel
import com.example.twittersearchclient.utils.GeneralUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
    application: Application,
    private val dataRepository: DataRepository
) : AndroidViewModel(application) {

    fun getInternetConnectionStatus(): Boolean{
        return GeneralUtility.checkInternet(getApplication())
    }

    fun callApiToFetchTweets(): LiveData<ApiResultData<ApiResponseModel>>{
        return flow {
            emit(ApiResultData.Loading())
            try {
                val apiResponseModel = dataRepository.getAllTweetsFromApi()
                if (apiResponseModel.success){
                    emit(ApiResultData.Success(dataRepository.getAllTweetsFromApi()))
                } else {
                    emit(ApiResultData.Failed())
                }
            } catch (e: Exception){
                e.printStackTrace()
                emit(ApiResultData.Exception(e))
            }
        }.asLiveData(Dispatchers.IO)
    }


}