package app.com.patricksdeliveryboy.models

import com.fasterxml.jackson.annotation.JsonProperty

class PostAcceptRejectStatus (
    @field:JsonProperty("requestStatus")
    var requestStatus: String? = null
)