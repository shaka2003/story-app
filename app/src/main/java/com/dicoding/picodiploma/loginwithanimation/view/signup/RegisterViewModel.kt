package com.dicoding.picodiploma.loginwithanimation.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.api.response.ErrorResponse
import com.dicoding.picodiploma.loginwithanimation.data.api.response.RegisterResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.lang.Exception

class RegisterViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private var _result = MutableLiveData<RegisterResponse>()
    val result : LiveData<RegisterResponse> =_result

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    suspend fun register(name: String, email: String, password: String) {
        try {
            _isLoading.value = true
            //get success message
            val message = repository.register(name, email, password)
            _result.value = message
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            _result.value = RegisterResponse(true, errorMessage)
        } catch (e : Exception) {
            _result.value = RegisterResponse(true, e.message.toString())
        } finally {
            _isLoading.value = false
        }
    }
}