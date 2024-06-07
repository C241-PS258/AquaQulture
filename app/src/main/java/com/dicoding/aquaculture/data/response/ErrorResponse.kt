package com.dicoding.aquaculture.data.response

data class ErrorResponse(
    val message: String,
    val error: String,
    val statusCode: Int
)
