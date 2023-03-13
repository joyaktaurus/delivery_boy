package app.com.patricksdeliveryboy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class PostLogin(
    @field:JsonProperty("userName")
    var userName: String? = null,

    @field:JsonProperty("password")
    var password: String? = null,
)