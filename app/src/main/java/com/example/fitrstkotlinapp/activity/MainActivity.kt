package com.example.fitrstkotlinapp.activity

import android.app.Activity
import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import android.content.Intent.getIntent
import android.net.Uri
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
//import androidx.activity.result.launch
import androidx.activity.result.ActivityResultLauncher
import kotlinx.android.synthetic.main.activity_main.*
import androidx.activity.result.contract.ActivityResultContract
import kotlinx.android.synthetic.main.card_post.view.*


class MainActivity : AppCompatActivity() {
    val viewModel: PostViewModel by viewModels()
    private val newPostRequestCode = 1
    private val editPostRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = PostsAdapter(object : OnInteractionListener {

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onRepost(post: Post) {
                viewModel.repostById(post.id)
            }


            override fun onShare(post: Post) {
                val myIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(myIntent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }

            override fun onVideo(post: Post) {
                if (post.video.isNullOrEmpty()){
                    Toast.makeText(
                        this@MainActivity,
                        "No video",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    val videoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    startActivity(videoIntent)
                }
            }

            override fun onEdit(post: Post) {
                val editIntent = Intent(this@MainActivity, EditPostActivity::class.java)
                editIntent.putExtra(Intent.EXTRA_TEXT, post.content)
                viewModel.edit(post)
                startActivityForResult(editIntent, editPostRequestCode)

            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this, { posts ->
            adapter.submitList(posts)

        })


//        viewModel.edited.observe(this) {
//            binding.content.setText(it.content)
//            if (it.content.isNotBlank()) {
//                binding.group.visibility = View.VISIBLE
//                binding.content.requestFocus()
//            }
//        }

        binding.fab.setOnClickListener {
            val newPostIntent = Intent(this@MainActivity, NewPostActivity::class.java)
            startActivityForResult(newPostIntent, newPostRequestCode)
        }


//        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
//            result ?: return@registerForActivityResult
//            viewModel.changeContent(result.toString())
//            viewModel.save()
//        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            newPostRequestCode -> {
                if (resultCode != Activity.RESULT_OK) {
                    return
                }
                data?.getStringExtra(Intent.EXTRA_TEXT)?.let {
                    viewModel.changeContent(it)
                    viewModel.save()

                }
            }

            editPostRequestCode -> {
                if (resultCode != Activity.RESULT_OK) {
                    return
                }
                val editedContent = data?.getStringExtra(Intent.EXTRA_TEXT)!!
                viewModel.changeContent(editedContent)
                viewModel.save()

            }
        }
    }
}
// override fun onEdit(post: Post) {
//                viewModel.edit(post)}


//        binding.saveButton.setOnClickListener {
//            with(binding.content) {
//                if (text.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        context.getString(R.string.error_empty_content),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//                viewModel.changeContent(text.toString())
//                viewModel.save()
//
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//                binding.group.visibility = View.GONE
//            }
//        }

//        binding.cancelButton.setOnClickListener {
//            with(binding.content) {
//                setText("")
//                viewModel.cancel()
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//                binding.group.visibility = View.GONE
//            }
//        }

