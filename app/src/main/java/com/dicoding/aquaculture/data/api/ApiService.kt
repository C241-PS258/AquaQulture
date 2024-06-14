package com.dicoding.aquaculture.data.api

import com.dicoding.aquaculture.data.response.HistoryResponse
import com.dicoding.aquaculture.data.response.LoginRequest
import com.dicoding.aquaculture.data.response.PredictResponse
import com.dicoding.aquaculture.data.response.RegisterRequest
import com.dicoding.aquaculture.data.response.RegisterResponse
import com.dicoding.aquaculture.data.response.StatusResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<ResponseBody>

    @GET("auth/status")
    suspend fun status(): StatusResponse

    @GET("dashboard")
    suspend fun getHistoryData(): List<HistoryResponse>

    @Multipart
    @POST("predict/fish")
    suspend fun predict(
        @Part image: MultipartBody.Part
    ): PredictResponse
}