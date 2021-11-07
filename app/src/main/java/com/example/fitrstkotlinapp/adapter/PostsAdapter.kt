package com.example.fitrstkotlinapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fitrstkotlinapp.viewmodel.kiloLogic
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.fitrstkotlinapp.dto.Post
import com.example.fitrstkotlinapp.R
import com.example.fitrstkotlinapp.databinding.CardPostBinding

//typealias OnLikeListener = (post: Post/*, itemId:String*/) -> Unit
//typealias OnRepostListener = (post: Post) -> Unit
//typealias OnRemoveListener = (post: Post) -> Unit

//interface OnInteractionListener {
//    fun onLike(post: Post) {}
//    fun onEdit(post: Post) {}
//    fun onRemove(post: Post) {}
//    fun onRepost(post: Post) {}
//
//}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener
//    private val onLikeListener: OnLikeListener,
//    private val onRepostListener: OnRepostListener,
//    private val onRemoveListener: OnRemoveListener

) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
//    var list = emptyList<Post>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(
            binding, /*-onLikeListener, onRepostListener, onRemoveListener--*/
            onInteractionListener
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    // override fun getItemCount(): Int = list.size
}

class PostViewHolder(
    private val binding: CardPostBinding,
//    private val onLikeListener: OnLikeListener,
//    private val onRepostListener: OnRepostListener,
//    private val onRemoveListener: OnRemoveListener
    private val onInteractionListener: OnInteractionListener
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
                onInteractionListener.onLike(post /*, likeButton.id.toString()*/)
                // onLikeListener(post)
            }
            repostButton.setOnClickListener {
                //onRepostListener(post)
                onInteractionListener.onRepost(post /*,, repostButton.id.toString()*/)
            }
//            saveButton.setOnClickListener{
//                OnInteractionListener.onEdit(post)
//            }


            moreButton.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                //    onRemoveListener(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()

            }


        }
    }

//    binding.likeButton.setOnClickListener {
//        viewModel.like()
//    }
//    binding.repostButton.setOnClickListener{
//        viewModel.repost()
//    }

}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}