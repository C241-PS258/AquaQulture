package com.dicoding.aquaculture.data.di

import android.content.Context
import com.dicoding.aquaculture.data.UserRepository
import com.dicoding.aquaculture.data.api.ApiConfig
import com.dicoding.aquaculture.data.pref.UserPreference
import com.dicoding.aquaculture.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(pref, apiService)
    }
}