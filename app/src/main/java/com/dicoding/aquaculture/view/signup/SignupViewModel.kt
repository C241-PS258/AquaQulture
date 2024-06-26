package com.dicoding.aquaculture.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.aquaculture.data.UserRepository
import com.dicoding.aquaculture.data.response.RegisterResponse
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: UserRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(name: String, email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.register(name, email, password)
                _registerResult.value = result
            } catch (e: Exception) {
                _registerResult.value = RegisterResponse(message = e.message)
            } finally {
                _isLoading.value = false
            }
        }
    }
}