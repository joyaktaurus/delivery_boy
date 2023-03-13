package app.com.patricksdeliveryboy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderedItemResponse(

	@field:JsonProperty("msg")
	val msg: String? = null,

	@field:JsonProperty("data")
	val data: List<DataItemOrder>? = null,

	@field:JsonProperty("error")
	val error: Boolean? = null,

	@field:JsonProperty("statusCode")
	val statusCode: Int? = null
)

data class DataItemOrder(

	@field:JsonProperty("quantity")
	val quantity: Int? = null,

	@field:JsonProperty("price")
	val price: Int? = null,

	@field:JsonProperty("name")
	val name: String? = null
)
