package app.com.patricksdeliveryboy.models

import com.google.gson.annotations.SerializedName

data class DashboardResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("data")
	val data: DData? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class DData(

	@field:SerializedName("cashInHand")
	val cashInHand: Int? = null,

	@field:SerializedName("noOfTodaysDeliveries")
	val noOfTodaysDeliveries: Int? = null,

	@field:SerializedName("todayEarnedAmount")
	val todayEarnedAmount: Int? = null
)
