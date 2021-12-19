package com.example.fitrstkotlinapp.dto

import android.os.Parcel
import android.os.Parcelable

data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val reposts: Int = 0,
    val seen: Int = likes + reposts,
    val video: String = "https://www.youtube.com/watch?v=WhWc3b3KhnY\""

)