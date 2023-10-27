package com.dicoding.picodiploma.loginwithanimation.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.story.activity.StoryActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()

        viewModel.isLoading.observe(this) {
            binding.progressLogin.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.result.observe(this) {
            if (it.error == false) {
                val email = binding.emailEditText.text.toString()
                viewModel.saveSession(UserModel(email, it.loginResult?.token!!))

                AlertDialog.Builder(this).apply {
                    setTitle("Berhasil Login!")
                    setMessage("Selamat datang $email")
                    setPositiveButton("Lanjut") { _, _ ->
                        val intent = Intent(this@LoginActivity, StoryActivity::class.java)
                        ViewModelFactory.refreshObject()
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }

            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Gagal!")
                    setMessage("tidak bisa login")
                    setPositiveButton("Lanjut") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.getSession().isLogin) {
            finish()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch {
                viewModel.login(email, password)
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(400)
        val emailTitle = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(400)
        val email = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(400)
        val passwordTitle = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(400)
        val password = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(400)
        val signup = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(1000)


        val emailSection = AnimatorSet().apply {
            playTogether(emailTitle, email)
        }

        val passSection = AnimatorSet().apply {
            playTogether(passwordTitle, password)
        }

        AnimatorSet().apply {
            playSequentially(title, emailSection, passSection, signup)
            start()
        }
    }

}