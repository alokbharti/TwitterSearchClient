package com.example.twittersearchclient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twittersearchclient.databinding.ItemTweetBinding
import com.example.twittersearchclient.model.Tweet

class TweetAdapter(tweets: List<Tweet>): RecyclerView.Adapter<TweetAdapter.ViewHolder>() {

    val tweetList = tweets

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTweetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tweetList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = tweetList[position]
        holder.bind(tweet)
    }

    class ViewHolder(binding: ItemTweetBinding): RecyclerView.ViewHolder(binding.root){
        private val itemTweetBinding = binding
        fun bind(tweet: Tweet){
            itemTweetBinding.tweet = tweet
            itemTweetBinding.executePendingBindings()
        }
    }
}