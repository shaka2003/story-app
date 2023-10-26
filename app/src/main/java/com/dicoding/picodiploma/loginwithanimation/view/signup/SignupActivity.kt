package com.dicoding.picodiploma.loginwithanimation.view.signup

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
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginActivity
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

        viewModel.isLoading.observe(this) {
            binding.pbRegister.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.result.observe(this) {
            if (it.error == false) {
                val email = binding.emailEditText.text
                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
                    setPositiveButton("Lanjut") { _, _ ->
                        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    create()
                    show()
                }

            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Gagal!")
                    setMessage("akun tidak bisa dibuat")
                    setPositiveButton("Lanjut") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
            }
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
        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val name = binding.nameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch {
                viewModel.register(name, email, password)
            }
        }
    }
}