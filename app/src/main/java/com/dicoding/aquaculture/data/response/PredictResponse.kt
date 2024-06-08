package com.dicoding.aquaculture.data.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("jenis_ikan")
	val jenisIkan: String? = null,

	@field:SerializedName("pakan")
	val pakan: String? = null,

	@field:SerializedName("pemeliharaan")
	val pemeliharaan: String? = null
)
