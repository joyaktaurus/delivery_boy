package app.com.patricksdeliveryboy.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponseModel(

	@field:JsonProperty("msg")
	val msg: String? = null,

	@field:JsonProperty("data")
	val data: Data? = null,

	@field:JsonProperty("error")
	val error: Boolean? = null,

	@field:JsonProperty("statusCode")
	val statusCode: Int? = null
)

@Entity(tableName = "User", indices = [Index(value = ["mobile"], unique = true)])
data class Data(
	@PrimaryKey(autoGenerate = true)
	var id:Int? = null,

	@field:JsonProperty("imageUrl")
	var imageUrl: String? = null,

	@field:JsonProperty("mobile")
	var mobile: String? = null,

	@field:JsonProperty("jwtToken")
	var jwtToken: String? = null,

	@field:JsonProperty("email")
	var email: String? = null
)
