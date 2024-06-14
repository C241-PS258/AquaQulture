package com.dicoding.aquaculture.data.pref

import com.dicoding.aquaculture.data.response.StatusResponse

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false,
    val status: StatusResponse? = null
)