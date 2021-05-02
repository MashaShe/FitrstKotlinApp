package com.example.fitrstkotlinapp.repository

import androidx.lifecycle.LiveData
import com.example.fitrstkotlinapp.dto.Post


interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun repost()

}