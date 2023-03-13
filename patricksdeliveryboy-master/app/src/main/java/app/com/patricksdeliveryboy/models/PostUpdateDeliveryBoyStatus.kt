package app.com.patricksdeliveryboy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class PostUpdateDeliveryBoyStatus(
    @field:JsonProperty("isOnDuty")
    var isOnDuty: Boolean? = null,
)