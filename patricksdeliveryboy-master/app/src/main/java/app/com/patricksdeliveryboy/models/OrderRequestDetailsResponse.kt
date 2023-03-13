package app.com.patricksdeliveryboy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderRequestDetailsResponse(

	@field:JsonProperty("msg")
	val msg: String? = null,

	@field:JsonProperty("data")
	val data: DataOrderRequest? = null,

	@field:JsonProperty("error")
	val error: Boolean? = null,

	@field:JsonProperty("statusCode")
	val statusCode: Int? = null
)

data class CartId(

	@field:JsonProperty("orderId")
	val orderId: Int? = null,

	@field:JsonProperty("_id")
	val id: String? = null
)

data class SellerIdsItem(

	@field:JsonProperty("lng")
	val lng: Double? = null,

	@field:JsonProperty("addressDetails")
	val addressDetails: AddressDetails? = null,

	@field:JsonProperty("storeName")
	val storeName: String? = null,

	@field:JsonProperty("id")
	val id: String? = null,

	@field:JsonProperty("lat")
	val lat: Double? = null
)

data class DataOrderRequest(

	@field:JsonProperty("cartId")
	val cartId: CartId? = null,

	@field:JsonProperty("_id")
	val id: String? = null,

	@field:JsonProperty("deliveryBoyId")
	val deliveryBoyId: DeliveryBoyId? = null,

	@field:JsonProperty("sellerIds")
	val sellerIds: List<SellerIdsItem>? = null,

	@field:JsonProperty("totalDistance")
	val totalDistance: Double? = null,

	@field:JsonProperty("totalCommission")
	val totalCommission: Double? = null
)

data class DeliveryBoyId(

	@field:JsonProperty("franchiseId")
	val franchiseId: String? = null,

	@field:JsonProperty("id")
	val id: String? = null
)

data class AddressDetails(

	@field:JsonProperty("location")
	val location: String? = null
)
