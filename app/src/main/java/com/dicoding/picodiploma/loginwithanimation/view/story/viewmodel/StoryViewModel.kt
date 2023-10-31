package com.dicoding.picodiploma.loginwithanimation.view.story.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ListStoryItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class StoryViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val storyPagingData: LiveData<PagingData<ListStoryItem>> =
        repository.getStoryPagingData().cachedIn(viewModelScope)

    fun getSession() = runBlocking { repository.getSession().first() }
}