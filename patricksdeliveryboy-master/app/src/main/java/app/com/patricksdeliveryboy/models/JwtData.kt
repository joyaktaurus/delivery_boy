package app.com.patricksdeliveryboy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class JwtData(

	@field:JsonProperty("data")
	val data: JwtItemData? = null,

	@field:JsonProperty("exp")
	val exp: Int? = null,

	@field:JsonProperty("iat")
	val iat: Int? = null
)

data class LocationData(
	@field:JsonProperty("lat")
	val latitude: Double? = null,

	@field:JsonProperty("lng")
	val longitude: Double? = null
)

data class JwtItemData(

	@field:JsonProperty("id")
	val id: String? = null,

	@field:JsonProperty("userRole")
	val userRole: String? = null
)
