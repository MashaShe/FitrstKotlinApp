package com.example.fitrstkotlinapp.repository

import androidx.lifecycle.LiveData
import com.example.fitrstkotlinapp.dto.Post


interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun repostById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)


}