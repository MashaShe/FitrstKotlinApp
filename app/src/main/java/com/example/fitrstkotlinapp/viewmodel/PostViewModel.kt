package com.example.fitrstkotlinapp.viewmodel
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitrstkotlinapp.dto.Post
import com.example.fitrstkotlinapp.repository.PostRepository
import com.example.fitrstkotlinapp.repository.PostRepositoryInMemoryImpl

val emptyPost = Post(
    id = 0L,
    author = "",
    content = "",
    published = "",
    likes = 0,
    likedByMe = false,
    reposts = 0
)
class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(emptyPost)

    fun likeById(id: Long) = repository.likeById(id)
    fun repostById(id:Long) = repository.repostById(id)
    fun removeById(id:Long) = repository.removeById(id)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = emptyPost
    }

    fun cancel() {
        edited.value = emptyPost
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

//    fun setVideoVisibility(post:Post): Int {
//        var returning =  View.GONE
//        if (!post.video.isNullOrEmpty()){
//          var returning = View.VISIBLE
//        }
//        return returning
//    }

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


