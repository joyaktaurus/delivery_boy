package app.com.patricksdeliveryboy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateDutyStatusResponseModel(

	@field:JsonProperty("msg")
	val msg: String? = null,

	@field:JsonProperty("data")
	val data: Any? = null,

	@field:JsonProperty("error")
	val error: Boolean? = null,

	@field:JsonProperty("statusCode")
	val statusCode: Int? = null
)
