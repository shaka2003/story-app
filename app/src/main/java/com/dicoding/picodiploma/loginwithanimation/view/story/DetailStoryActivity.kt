package com.dicoding.picodiploma.loginwithanimation.view.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<ListStoryItem>("extra_user") as ListStoryItem

        Glide.with(this)
            .load(user.photoUrl)
            .error(R.drawable.ic_launcher_background)
            .into(binding.storyImage)
        binding.storyTitle.text = user.name
        binding.storyDescription.text = user.description
    }
}