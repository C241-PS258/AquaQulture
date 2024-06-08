package com.dicoding.aquaculture.data.response

data class RegisterResponse(
	val password: String? = null,
	val name: String? = null,
	val id: String? = null,
	val email: String? = null,
	val message: String?,
	val statusCode: Int? = null
)

