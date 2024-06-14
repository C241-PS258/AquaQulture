package com.dicoding.aquaculture.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryResponse(
	@field:SerializedName("idUser")
	val idUser: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nameFish")
	val nameFish: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null,

	@field:SerializedName("harvestPredictions")
	val harvestPredictions: String? = null,

	@field:SerializedName("fish")
	val fish: Fish? = null
) : Parcelable

@Parcelize
data class Fish(
	@field:SerializedName("pakan")
	val pakan: String? = null,

	@field:SerializedName("pemeliharaan")
	val pemeliharaan: String? = null
) : Parcelable
