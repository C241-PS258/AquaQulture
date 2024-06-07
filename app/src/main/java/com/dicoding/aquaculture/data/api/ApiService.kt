package com.dicoding.aquaculture.data.api

import com.dicoding.aquaculture.data.response.DetailStoryResponse
import com.dicoding.aquaculture.data.response.LoginRequest
import com.dicoding.aquaculture.data.response.LoginResponse
import com.dicoding.aquaculture.data.response.RegisterRequest
import com.dicoding.aquaculture.data.response.RegisterResponse
import com.dicoding.aquaculture.data.response.StatusResponse
import com.dicoding.aquaculture.data.response.StoryResponse
import com.dicoding.aquaculture.data.response.StoryUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): StoryResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location : Int = 1,
    ): StoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStory(
        @Path("id") id:String
    ) : DetailStoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Float?,
        @Part("lon") lon: Float?
    ): StoryUploadResponse
}