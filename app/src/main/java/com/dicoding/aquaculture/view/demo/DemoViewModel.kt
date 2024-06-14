package com.dicoding.aquaculture.view.demo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.aquaculture.data.UserRepository
import com.dicoding.aquaculture.data.response.PredictResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class DemoViewModel(private val repository: UserRepository) : ViewModel() {

    private val _predictResult = MutableLiveData<PredictResponse?>()
    val predictResult: MutableLiveData<PredictResponse?> get() = _predictResult
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage
    private val _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean> get() = _isLoading

    fun predictFish(image: MultipartBody.Part) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.predictFish(image)
                _predictResult.value = response
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error predicting fish: ${e.message}", e)
                _errorMessage.value = "Error predicting fish: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetPredictResult() {
        _predictResult.value = null
    }

    fun resetErrorMessage() {
        _errorMessage.value = null
    }
}