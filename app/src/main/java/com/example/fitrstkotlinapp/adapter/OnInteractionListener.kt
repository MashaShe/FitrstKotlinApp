package com.example.fitrstkotlinapp.adapter

import com.example.fitrstkotlinapp.dto.Post

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onRepost(post: Post) {}

}