package com.example.fitrstkotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fitrstkotlinapp.databinding.ActivityMainBinding
import com.example.fitrstkotlinapp.dto.Post
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false
        )

        with(binding) {
            headerTextView.text = post.author
            timeTextView.text = post.published
            longTextView.text = post.content
            repostNumTextView.text= kiloLogic(post.reposts)

            if (post.likedByMe) {
                likeButton?.setImageResource(R.drawable.ic_baseline_favorite_filled_24)
            }

            likesNumTextView?.text = post.likes.toString()

            root.setOnClickListener {
                Log.d("stuff", "root click")
            }

            icon_imageView.setOnClickListener {
                Log.d("stuff", "avatar click")
            }
            moreButton?.setOnClickListener {
                Log.d("stuff", "more click")
            }

            likeButton?.setOnClickListener {
                Log.d("stuff", "like click")
            }

            likeButton?.setOnClickListener {
                Log.d("stuff", "like action click")
                post.likedByMe = !post.likedByMe
                likeButton.setImageResource(
                    if (post.likedByMe) R.drawable.ic_baseline_favorite_filled_24 else R.drawable.ic_baseline_favorite_border_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likesNumTextView?.text = kiloLogic(post.likes)
            }

            repostButton?.setOnClickListener{
                Log.d("stuff", "like")
                post.reposts++
                repostNumTextView?.text = kiloLogic(post.reposts)
            }

        }
    }
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