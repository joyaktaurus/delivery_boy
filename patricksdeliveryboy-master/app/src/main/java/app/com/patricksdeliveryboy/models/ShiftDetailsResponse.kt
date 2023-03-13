package app.com.patricksdeliveryboy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class ShiftDetailsResponse(

	@field:JsonProperty("msg")
	val msg: String? = null,

	@field:JsonProperty("data")
	val data: DataShiftDetails? = null,

	@field:JsonProperty("error")
	val error: Boolean? = null,

	@field:JsonProperty("statusCode")
	val statusCode: Int? = null
)

data class ItemsItem(

	@field:JsonProperty("dayTotalTime")
	val dayTotalTime: Double? = null,

	@field:JsonProperty("_id")
	val id: String? = null,

	@field:JsonProperty("list")
	val list: List<ListItem>? = null
)

data class ListItem(

	@field:JsonProperty("totalTime")
	val totalTime: Double? = null,

	@field:JsonProperty("startTime")
	val startTime: String? = null,

	@field:JsonProperty("id")
	val id: String? = null,

	@field:JsonProperty("endTime")
	val endTime: String? = null
)

data class DataShiftDetails(

	@field:JsonProperty("perPage")
	val perPage: Int? = null,

	@field:JsonProperty("hasNextPage")
	val hasNextPage: Boolean? = null,

	@field:JsonProperty("totalPages")
	val totalPages: Int? = null,

	@field:JsonProperty("totalCount")
	val totalCount: Int? = null,

	@field:JsonProperty("items")
	val items: List<ItemsItem>? = null
)
