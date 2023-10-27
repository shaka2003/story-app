package com.dicoding.picodiploma.loginwithanimation.view.story.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ListStoryItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StoryViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _storyResponseLiveData = MutableLiveData<List<ListStoryItem?>?>()
    var storyResponseLiveData: MutableLiveData<List<ListStoryItem?>?> = _storyResponseLiveData

    init {
        getListStory()
    }

    fun getListStory() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                _storyResponseLiveData.postValue(repository.getListStory())
                _isLoading.postValue(false)
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.postValue(false)
            }
        }
    }

    fun getSession() = runBlocking { repository.getSession().first() }
}