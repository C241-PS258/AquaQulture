package com.dicoding.aquaculture.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.aquaculture.data.UserRepository
import com.dicoding.aquaculture.data.pref.UserModel
import com.dicoding.aquaculture.data.response.HistoryResponse
import com.dicoding.aquaculture.data.response.PredictResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val userName: MutableLiveData<String?> = MutableLiveData()
    private val userEmail: MutableLiveData<String?> = MutableLiveData()

    private val _historyData = MutableLiveData<List<HistoryResponse>>()
    val historyData: LiveData<List<HistoryResponse>> = _historyData

    private val _historyMessage = MutableLiveData<String?>()
    val historyMessage: LiveData<String?> get() = _historyMessage

    private val _predictResult = MutableLiveData<PredictResponse?>()
    val predictResult: MutableLiveData<PredictResponse?> get() = _predictResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    init {
        getUsernameOnLaunch()
        fetchHistoryData()
        getEmail()
    }

    private fun getUsernameOnLaunch() {
        viewModelScope.launch {
            try {
                val token = repository.getUserToken()
                val statusResponse = repository.getStatus(token)
                val name = statusResponse.name
                userName.postValue(name)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching status: ${e.message}", e)
                userName.postValue(null)
            }
        }
    }

    private fun getEmail() {
        viewModelScope.launch {
            try {
                val token = repository.getUserToken()
                val statusResponse = repository.getStatus(token)
                val email = statusResponse.email
                userEmail.postValue(email)
            } catch (e : Exception) {
                Log.e("MainViewModel", "Error fetching status: ${e.message} ",e )
                userEmail.postValue(null)
            }
        }
    }

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

    fun fetchHistoryData() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val data = repository.getHistoryData()
                _historyData.postValue(data)
            } catch (e: Exception) {
                _historyMessage.postValue("Error fetching history data: ${e.message}")
                Log.e("MainViewModel", "Error fetching history data: ${e.message}", e)
            }
        }
    }

    fun resetPredictResult() {
        _predictResult.value = null
    }

    fun resetErrorMessage() {
        _errorMessage.value = null
    }

    fun getUserName(): MutableLiveData<String?> = userName

    fun getUserEmail(): MutableLiveData<String?> = userEmail

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
