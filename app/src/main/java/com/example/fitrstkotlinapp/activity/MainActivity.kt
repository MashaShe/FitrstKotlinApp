package com.example.fitrstkotlinapp.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fitrstkotlinapp.databinding.ActivityMainBinding
import com.example.fitrstkotlinapp.viewmodel.PostViewModel
import androidx.lifecycle.observe
import com.example.fitrstkotlinapp.adapter.PostsAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter() {
            viewModel.likeById(it.id)
            // viewModel.repostById(it.id)
        }
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.list = posts
        }
    }
}