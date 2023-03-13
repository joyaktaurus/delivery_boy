package app.com.patricksdeliveryboy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderAcceptRejectResponse(

	@field:JsonProperty("msg")
	val msg: String? = null,

	@field:JsonProperty("data")
	val data: List<DataItem?>? = null,

	@field:JsonProperty("error")
	val error: Boolean? = null,

	@field:JsonProperty("statusCode")
	val statusCode: Int? = null
)

data class AddressDetailsOrder(

	@field:JsonProperty("location")
	val location: String? = null
)

data class DataItem(

	@field:JsonProperty("lng")
	val lng: Double? = null,

	@field:JsonProperty("sellerName")
	val sellerName: String? = null,

	@field:JsonProperty("addressDetails")
	val addressDetails: AddressDetailsOrder? = null,

	@field:JsonProperty("storeName")
	val storeName: String? = null,

	@field:JsonProperty("id")
	val id: String? = null,

	@field:JsonProperty("lat")
	val lat: Double? = null
)
