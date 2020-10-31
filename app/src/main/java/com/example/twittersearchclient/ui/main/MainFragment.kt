package com.example.twittersearchclient.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.twittersearchclient.R
import com.example.twittersearchclient.adapter.TweetAdapter
import com.example.twittersearchclient.data.network.ApiResultData
import com.example.twittersearchclient.databinding.MainFragmentBinding
import com.example.twittersearchclient.model.Tweet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModels<MainViewModel>()
    private var tweetList: ArrayList<Tweet> = ArrayList()
    private lateinit var adapter: TweetAdapter
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TweetAdapter(tweetList)
        binding.adapter = adapter

        viewModel.getAllTweetsFromDb().observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()){
                val isInternetAvailable = viewModel.getInternetConnectionStatus()
                if (isInternetAvailable) {
                    viewModel.callApiToFetchTweets()
                } else {
                    //show error in UI
                }
            } else {
                tweetList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

    }

}