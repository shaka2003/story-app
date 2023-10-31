package com.dicoding.picodiploma.loginwithanimation.view.story.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityStoryBinding
import com.dicoding.picodiploma.loginwithanimation.databinding.ListStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.logout.LogOutViewModel
import com.dicoding.picodiploma.loginwithanimation.view.map.MapsActivity
import com.dicoding.picodiploma.loginwithanimation.view.story.adapter.Adapter
import com.dicoding.picodiploma.loginwithanimation.view.story.adapter.LoadingStateAdapter
import com.dicoding.picodiploma.loginwithanimation.view.story.viewmodel.StoryViewModel
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity

class StoryActivity : AppCompatActivity(), Adapter.ItemClickListener {

    private val viewModel: StoryViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private val logoutViewModel by viewModels<LogOutViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityStoryBinding

    private val storyAdapter = Adapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvStory.layoutManager = LinearLayoutManager(this)
        binding.rvStory.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storyAdapter.retry()
            }
        )

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddStoriesActivity::class.java)
            startActivity(intent)
        }

        viewModel.storyPagingData.observe(this) { pagingData ->
            storyAdapter.submitData(lifecycle, pagingData)
        }

        viewModel.isLoading.observe(this) {
            binding.pbStory.visibility = if (it) View.VISIBLE else View.GONE
        }

    }

    override fun onItemClick(user: ListStoryItem, itemView: ListStoryBinding) {
        val intent = Intent(this, DetailStoryActivity::class.java)
        intent.putExtra("extra_user", user)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        if (!viewModel.getSession().isLogin) {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        } else {
            viewModel.storyPagingData
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                logoutViewModel.logout()

                AlertDialog.Builder(this).apply {
                    setTitle("Berhasil Logout!")
                    setMessage("Anda telah logout")
                    setPositiveButton("Lanjut") { _, _ ->
                        val intent = Intent(this@StoryActivity, WelcomeActivity::class.java)
                        ViewModelFactory.refreshObject()
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            }

            R.id.maps -> {
                val intent = Intent(this@StoryActivity, MapsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}