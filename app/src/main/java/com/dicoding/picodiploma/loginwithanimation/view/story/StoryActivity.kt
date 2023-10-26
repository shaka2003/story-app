package com.dicoding.picodiploma.loginwithanimation.view.story

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityStoryBinding
import com.dicoding.picodiploma.loginwithanimation.databinding.ListStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.logout.LogOutActivity
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity

class StoryActivity : AppCompatActivity(), Adapter.ItemClickListener {

    private val viewModel: StoryViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityStoryBinding

    private val storyAdapter = Adapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //nampilin toolbar
        setSupportActionBar(binding.toolbar)

        binding.rvStory.layoutManager = LinearLayoutManager(this)
        binding.rvStory.adapter = storyAdapter

        //listener action button tambah
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddStoriesActivity::class.java)
            startActivity(intent)
        }

        viewModel.storyResponseLiveData.observe(this) {
            storyAdapter.submitList(it)
        }

        viewModel.isLoading.observe(this) {
            binding.pbStory.visibility = if (it) View.VISIBLE else View.GONE
        }

    }


    //to detail act
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
            viewModel.getListStory()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profil -> {
                Intent(this, LogOutActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}