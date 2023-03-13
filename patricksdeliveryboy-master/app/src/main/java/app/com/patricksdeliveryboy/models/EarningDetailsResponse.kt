package app.com.patricksdeliveryboy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class EarningDetailsResponse(

	@field:JsonProperty("msg")
	val msg: String? = null,

	@field:JsonProperty("data")
	val data: DataEarningDeatils? = null,

	@field:JsonProperty("error")
	val error: Boolean? = null,

	@field:JsonProperty("statusCode")
	val statusCode: Int? = null
)

data class DataEarningDeatils(

	@field:JsonProperty("perPage")
	val perPage: Int? = null,

	@field:JsonProperty("hasNextPage")
	val hasNextPage: Boolean? = null,

	@field:JsonProperty("totalPages")
	val totalPages: Int? = null,

	@field:JsonProperty("totalCount")
	val totalCount: Int? = null,

	@field:JsonProperty("items")
	val items: List<ItemsItemEarningDetails>? = null
)

data class ListItemEarningDetails(

	@field:JsonProperty("firstName")
	val firstName: String? = null,

	@field:JsonProperty("lastName")
	val lastName: String? = null,

	@field:JsonProperty("id")
	val id: String? = null,

	@field:JsonProperty("commissionAmount")
	val commissionAmount: Int? = null,

	@field:JsonProperty("deliveredAt")
	val deliveredAt: String? = null
)

data class ItemsItemEarningDetails(

	@field:JsonProperty("totalCommissionAmount")
	val totalCommissionAmount: Int? = null,

	@field:JsonProperty("_id")
	val id: String? = null,

	@field:JsonProperty("list")
	val list: List<ListItemEarningDetails>? = null
)
