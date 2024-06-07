package com.dicoding.aquaculture.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.aquaculture.data.UserRepository
import com.dicoding.aquaculture.data.pref.UserModel
import com.dicoding.aquaculture.data.response.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<String?>()
    val loginResult: LiveData<String?> = _loginResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val token = repository.login(email, password)
                _loginResult.postValue(token)
                val user = UserModel(email = email, token = token, isLogin = true)
                saveSession(user)
            } catch (e: HttpException) {
                val errorResponse = e.response()?.errorBody()?.string()
                val errorMessage = errorResponse ?: e.message()
                _errorMessage.postValue(errorMessage)
                _loginResult.postValue(null)
            } catch (e: IOException) {
                _errorMessage.postValue("Network error: ${e.message}")
                _loginResult.postValue(null)
            } catch (e: Exception) {
                _errorMessage.postValue(e.message ?: "Unknown error")
                _loginResult.postValue(null)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }


    private fun saveSession(user: UserModel) {
        viewModelScope.launch {
            try {
                repository.saveSession(user)
                _loginResult.postValue(user.token)
            } catch (e: Exception) {
                _errorMessage.postValue(e.message ?: "Unknown error")
                _loginResult.postValue(null)
            }
        }
    }
}