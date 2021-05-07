package com.example.fitrstkotlinapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fitrstkotlinapp.viewmodel.kiloLogic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitrstkotlinapp.dto.Post
import com.example.fitrstkotlinapp.R
import com.example.fitrstkotlinapp.databinding.CardPostBinding


typealias OnLikeListener = (post: Post/*, itemId:String*/) -> Unit
//typealias OnRepostListener = (post: Post) -> Unit


class PostsAdapter(
    private val onLikeListener: OnLikeListener//,
    //private val OnRepostListener: OnRepostListener
) : RecyclerView.Adapter<PostViewHolder>() {
    var list = emptyList<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(
            binding, onLikeListener//,OnRepostListener
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener//,
    //private val OnRepostListener: OnRepostListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            headerTextView.text = post.author
            timeTextView.text = post.published
            longTextView.text = post.content
            repostNumTextView.text = kiloLogic(post.reposts)
            likesNumTextView?.text = kiloLogic(post.likes)
            seenNumTextView.text = kiloLogic(post.seen)
            likeButton.setImageResource(
                if (post.likedByMe) R.drawable.ic_baseline_favorite_filled_24 else R.drawable.ic_baseline_favorite_border_24
            )

            likeButton.setOnClickListener {
                onLikeListener(post /*, likeButton.id.toString()*/)
            }
            //repostButton.setOnClickListener {
            //    onLikeListener(post, repostButton.id.toString()
            //  }
        }
    }
}

//    binding.likeButton.setOnClickListener {
//        viewModel.like()
//    }
//    binding.repostButton.setOnClickListener{
//        viewModel.repost()
//    }

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}