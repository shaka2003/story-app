package com.dicoding.picodiploma.loginwithanimation.view.welcome

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class WelcomeViewModel(private val repository: UserRepository) : ViewModel() {

    fun getSession() = runBlocking {
        repository.getSession().first()
    }
}