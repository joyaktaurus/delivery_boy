package app.com.patricksdeliveryboy.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
data class ProfileResponseModel(

	@field:JsonProperty("msg")
	val msg: String? = null,

	@field:JsonProperty("data")
	val data: DataProfile? = null,

	@field:JsonProperty("error")
	val error: Boolean? = null,

	@field:JsonProperty("statusCode")
	val statusCode: Int? = null
) : Parcelable

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
data class DataProfile(

	@field:JsonProperty("shiftId")
	val shiftId: String? = null,

	@field:JsonProperty("pincode")
	val pincode: String? = null,

	@field:JsonProperty("isAvailable")
	val isAvailable: Boolean? = null,

	@field:JsonProperty("address")
	val address: String? = null,

	@field:JsonProperty("profileImageId")
	val profileImageId: String? = null,

	@field:JsonProperty("tsCreatedAt")
	val tsCreatedAt: Long? = null,

	@field:JsonProperty("mobile")
	val mobile: String? = null,

	@field:JsonProperty("deliveryBoyId")
	val deliveryBoyId: Int? = null,

	@field:JsonProperty("isEnabled")
	val isEnabled: Boolean? = null,

	@field:JsonProperty("__v")
	val V: Int? = null,

	@field:JsonProperty("franchiseId")
	val franchiseId: String? = null,

	@field:JsonProperty("location")
	val location: Location? = null,

	@field:JsonProperty("deliveryBoyName")
	val deliveryBoyName: String? = null,

	@field:JsonProperty("id")
	val id: String? = null,

	@field:JsonProperty("email")
	val email: String? = null
) : Parcelable

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
data class Location(

	@field:JsonProperty("lng")
	val lng: Double? = null,

	@field:JsonProperty("lat")
	val lat: Double? = null
) : Parcelable
