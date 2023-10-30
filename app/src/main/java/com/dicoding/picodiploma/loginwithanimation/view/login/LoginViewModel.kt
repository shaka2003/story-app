package com.dicoding.picodiploma.loginwithanimation.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ErrorResponse
import com.dicoding.picodiploma.loginwithanimation.data.api.response.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.lang.Exception

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private var _result = MutableLiveData<LoginResponse>()
    val result : LiveData<LoginResponse> =_result

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    suspend fun login(email: String, password: String) {
        try {
            _isLoading.value = true
            val message = repository.login(email, password)
            _result.value = message
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            _result.value = LoginResponse(null, true, errorMessage)
        } catch (e : Exception) {
            _result.value = LoginResponse(null, true, e.message.toString())
        } finally {
            _isLoading.value = false
        }
    }

    fun getSession() = runBlocking {
        repository.getSession().first()
    }
}