@file:Suppress("DEPRECATION")

package com.example.twittersearchclient.utils

import android.content.Context
import android.net.ConnectivityManager


object GeneralUtility {

    fun checkInternet(context: Context) : Boolean{
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (connectivityManager != null) {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        } else false
    }
}