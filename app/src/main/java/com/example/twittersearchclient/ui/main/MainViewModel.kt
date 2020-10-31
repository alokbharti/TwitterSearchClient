package com.example.twittersearchclient.ui.main

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import com.example.twittersearchclient.utils.GeneralUtility

class MainViewModel @ViewModelInject constructor(
    application: Application
) : AndroidViewModel(application) {

    fun getInternetConnectionStatus(): Boolean{
        return GeneralUtility.checkInternet(getApplication())
    }
}