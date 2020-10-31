package com.example.twittersearchclient.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twittersearchclient.R
import com.example.twittersearchclient.databinding.ItemTweetBinding
import com.example.twittersearchclient.model.Tweet

class TweetAdapter(tweets: List<Tweet>): RecyclerView.Adapter<TweetAdapter.ViewHolder>() {

    val tweetList = tweets

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTweetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int {
        return tweetList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = tweetList[position]
        holder.bind(tweet)
    }

    class ViewHolder(binding: ItemTweetBinding, private val context: Context): RecyclerView.ViewHolder(binding.root){
        private val itemTweetBinding = binding
        fun bind(tweet: Tweet){
            itemTweetBinding.tweet = tweet
            itemTweetBinding.executePendingBindings()
            Glide
                .with(context)
                .load(tweet.profileImageUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .into(itemTweetBinding.ivPerson)
        }
    }
}