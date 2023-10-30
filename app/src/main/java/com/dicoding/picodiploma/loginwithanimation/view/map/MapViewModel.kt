package com.dicoding.picodiploma.loginwithanimation.view.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ListStoryItem
import kotlinx.coroutines.launch

class MapViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _storyResponseLiveData = MutableLiveData<List<ListStoryItem?>?>()
    var storyResponseLiveData: MutableLiveData<List<ListStoryItem?>?> = _storyResponseLiveData

    fun getStoryLocation() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                _storyResponseLiveData.postValue(repository.getStoriesLocation())
                _isLoading.postValue(false)
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.postValue(false)
            }
        }
    }
}