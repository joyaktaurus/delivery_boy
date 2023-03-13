package app.com.patricksdeliveryboy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class OnesignalPayloadResponse(

	@field:JsonProperty("a")
	val A: A? = null,

	@field:JsonProperty("i")
	val I: String? = null
)

data class A(

	@field:JsonProperty("reference_id")
	val referenceId: String? = null,

	@field:JsonProperty("type")
	val type: String? = null
)
