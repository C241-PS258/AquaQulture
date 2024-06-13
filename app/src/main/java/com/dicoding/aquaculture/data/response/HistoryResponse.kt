package com.dicoding.aquaculture.data.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("idUser")
	val idUser: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nameFish")
	val namefish: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null,

	@field:SerializedName("harvestPredictions")
	val harvestPredictions: String? = null
)
