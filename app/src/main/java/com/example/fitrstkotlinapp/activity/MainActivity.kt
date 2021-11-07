package com.example.fitrstkotlinapp.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fitrstkotlinapp.databinding.ActivityMainBinding
import com.example.fitrstkotlinapp.viewmodel.PostViewModel
import androidx.lifecycle.observe
import com.example.fitrstkotlinapp.R
import com.example.fitrstkotlinapp.adapter.OnInteractionListener
import com.example.fitrstkotlinapp.adapter.PostsAdapter
import com.example.fitrstkotlinapp.dto.Post
import com.example.fitrstkotlinapp.util.AndroidUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

//        val adapter = PostsAdapter(
//            onLikeListener = { viewModel.likeById(it.id) },
//            onRepostListener = { viewModel.repostById(it.id)},
//            onRemoveListener = { viewModel.removeById(it.id)}
//        )

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {viewModel.edit(post)}
            override fun onLike(post: Post) {viewModel.likeById(post.id)}
            override fun onRemove(post: Post) {viewModel.removeById(post.id)}
            override fun onRepost(post: Post) {viewModel.repostById(post.id) }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this, { posts ->
            adapter.submitList(posts)
        })
        viewModel.edited.observe(this) {
            binding.content.setText(it.content)
            if (it.content.isNotBlank()){
              binding.group.visibility = View.VISIBLE
                binding.content.requestFocus()
            }
        }

        binding.saveButton.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                binding.group.visibility = View.VISIBLE
                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
                binding.group.visibility = View.GONE
            }
        }

        binding.cancelButton.setOnClickListener{
            with(binding.content) {
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
                binding.group.visibility = View.GONE
            }
        }

    }
}