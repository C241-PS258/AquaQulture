package com.dicoding.aquaculture.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.aquaculture.data.UserRepository
import com.dicoding.aquaculture.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val userName: MutableLiveData<String> = MutableLiveData()
    init {
        getUsernameOnLaunch()
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

    fun getUserName(): LiveData<String> = userName

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}