package org.techtown.finalproject2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.techtown.finalproject2.API.PostData
import org.techtown.finalproject2.databinding.ActivityPostDetailBinding

class PostDetail : AppCompatActivity() {

    private val binding : ActivityPostDetailBinding by lazy{
        ActivityPostDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val post = intent.getSerializableExtra("post") as PostData
        binding.title.text = post.title
        binding.content.text = post.content
        binding.where.text = post.where
        binding.time.text = post.date
    }
}