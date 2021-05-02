package com.example.fitrstkotlinapp.viewmodel
import androidx.lifecycle.ViewModel
import com.example.fitrstkotlinapp.repository.PostRepository
import com.example.fitrstkotlinapp.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like() = repository.like()
    fun repost() = repository.repost()
}

fun kiloLogic(num: Int): String {
    var returning = num.toString()

    when (true){
        num in 1000..1099 -> {returning = "1K"}
        num in 1100..9999 -> {returning =  "${(num.toDouble()/1000).toString().take(3)}K"}
        num in 10000..999999 -> {returning =  "${(num.toDouble()/1000).toString().take(2)}K"}
        num > 999999 -> {returning =  "${(num.toDouble()/1000000).toString().take(3)}M"}
    }

    return returning
}
