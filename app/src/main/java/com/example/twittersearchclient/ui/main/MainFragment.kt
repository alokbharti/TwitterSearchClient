package com.example.twittersearchclient.ui.main

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
    /**This will help to restore all tweets if user clears the search text*/
    private var tweetListCache: ArrayList<Tweet> = ArrayList()
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

        //show splash screen for 2 secs
        Handler().postDelayed({
            binding.clSplashScreen.visibility = View.GONE
        }, 2000)

        viewModel.apiNetworkState.observe(viewLifecycleOwner, Observer {
            when(it){
                is ApiResultData.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.tvApiState.visibility = View.GONE
                }
                is ApiResultData.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.tvApiState.text = getString(R.string.loading_data)
                }
                is ApiResultData.Failed -> {
                    binding.pbLoading.visibility = View.INVISIBLE
                    binding.tvApiState.text = getString(R.string.failed_api_response)
                }
                is ApiResultData.Exception -> {}
            }
        })

        viewModel.getAllTweetsFromDb().observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()){
                val isInternetAvailable = viewModel.getInternetConnectionStatus()
                if (isInternetAvailable) {
                    viewModel.callApiToFetchTweets()
                } else {
                    binding.pbLoading.visibility = View.INVISIBLE
                    binding.tvApiState.text = getString(R.string.failed_api_response)
                }
            } else {
                tweetListCache.addAll(it)
                tweetList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.getAllFilteredTweetsFromDb().observe(viewLifecycleOwner, Observer {
            tweetList.clear()
            if (it.isNullOrEmpty()){
                binding.tvApiState.text = getString(R.string.no_tweets_found)
            } else {
                binding.tvApiState.text = ""
                tweetList.addAll(it)
            }
            adapter.notifyDataSetChanged()
        })

        binding.btnSearch.setOnClickListener{
            val searchedText = binding.tetSearch.text.toString()
            if (searchedText.isNotEmpty()){
                viewModel.searchTweets(searchedText)
            }
        }

        binding.tetSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text.isNotEmpty()){
                    binding.ivCloseSearch.visibility = View.VISIBLE
                } else {
                    binding.ivCloseSearch.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.ivCloseSearch.setOnClickListener{
            binding.tetSearch.setText("")
            binding.tetSearch.clearFocus()
            binding.tvApiState.text = ""

            //show all tweets
            tweetList.clear()
            tweetList.addAll(tweetListCache)
            adapter.notifyDataSetChanged()
        }
    }

}