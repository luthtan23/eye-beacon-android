package com.luthtan.eye_beacon_android.domain.response.dashboard

import com.google.gson.annotations.SerializedName

data class BleResponse(

	@field:SerializedName("status")
	val status: Status? = null
)

data class Status(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("isInside")
	val isInside: Boolean? = null,

	@field:SerializedName("room")
	val room: String? = null
)
