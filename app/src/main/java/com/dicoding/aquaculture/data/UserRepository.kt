package com.dicoding.aquaculture.data

import android.util.Log
import com.dicoding.aquaculture.data.api.ApiConfig.getApiService
import com.dicoding.aquaculture.data.api.ApiService
import com.dicoding.aquaculture.data.response.ErrorResponse
import com.dicoding.aquaculture.data.pref.UserModel
import com.dicoding.aquaculture.data.pref.UserPreference
import com.dicoding.aquaculture.data.response.DetailStoryResponse
import com.dicoding.aquaculture.data.response.LoginRequest
import com.dicoding.aquaculture.data.response.LoginResponse
import com.dicoding.aquaculture.data.response.RegisterRequest
import com.dicoding.aquaculture.data.response.RegisterResponse
import com.dicoding.aquaculture.data.response.StatusResponse
import com.dicoding.aquaculture.data.response.StoryUploadResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    suspend fun login(email: String, password: String): String {
        val request = LoginRequest(email, password)
        val response = apiService.login(request)
        return if (response.isSuccessful) {
            response.body()?.string() ?: throw Exception("No token in response")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            throw Exception(errorResponse.message)
        }
    }


    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        val request = RegisterRequest(name, email, password)
        return try {
            val response = apiService.register(request)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Registration successful but response body is null")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                throw Exception(errorResponse.message)
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = if (errorBody != null) {
                Gson().fromJson(errorBody, ErrorResponse::class.java)
            } else {
                ErrorResponse(e.message(), "Unknown error", e.code())
            }
            throw Exception(errorResponse.message)
        }
    }

    suspend fun getStatus(token: String): StatusResponse {
        return try {
            getApiService(token).status()
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = if (errorBody != null) {
                Gson().fromJson(errorBody, ErrorResponse::class.java).message
            } else {
                e.message()
            }
            throw Exception(errorMessage)
        }
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun getUserToken(): String {
        return getSession().first().token
    }

    suspend fun getDetailStory(storyId: String): DetailStoryResponse {
        return try {
            val token = getUserToken()
            getApiService(token).getDetailStory(storyId)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = if (errorBody != null) {
                Gson().fromJson(errorBody, DetailStoryResponse::class.java).message
            } else {
                e.message()
            }
            throw Exception(errorMessage)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun uploadStory(image: MultipartBody.Part, description: RequestBody, lat : Float?, lon : Float?): StoryUploadResponse {
        return try {
            val token = getUserToken()
            getApiService(token).uploadImage(image, description, lat, lon)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, StoryUploadResponse::class.java)
            throw Exception(errorResponse.message)
        }
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}