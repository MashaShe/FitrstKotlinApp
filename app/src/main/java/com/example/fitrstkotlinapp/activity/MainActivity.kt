package com.example.fitrstkotlinapp.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fitrstkotlinapp.R
import com.example.fitrstkotlinapp.databinding.ActivityMainBinding
import com.example.fitrstkotlinapp.dto.Post
import com.example.fitrstkotlinapp.viewmodel.PostViewModel
import com.example.fitrstkotlinapp.viewmodel.kiloLogic
import androidx.lifecycle.observe


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        //OWNER не определяется
        viewModel.data.observe(this) { post ->
            with(binding) {
                headerTextView.text = post.author
                timeTextView.text = post.published
                longTextView.text = post.content
                repostNumTextView.text= kiloLogic(post.reposts)
                likesNumTextView?.text = kiloLogic(post.likes)

                likeButton.setImageResource(
                 if (post.likedByMe) R.drawable.ic_baseline_favorite_filled_24 else R.drawable.ic_baseline_favorite_border_24
                )

            }
        }
        binding.likeButton.setOnClickListener {
            viewModel.like()
        }
        binding.repostButton.setOnClickListener{
            viewModel.repost()
        }
    }
}