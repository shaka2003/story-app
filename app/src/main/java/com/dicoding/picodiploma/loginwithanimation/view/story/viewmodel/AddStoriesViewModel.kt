package com.dicoding.picodiploma.loginwithanimation.view.story.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import java.io.File

class AddStoriesViewModel(private val repository: UserRepository) : ViewModel() {

    fun uploadImage(file: File, description: String) = repository.uploadImage(file, description)
}