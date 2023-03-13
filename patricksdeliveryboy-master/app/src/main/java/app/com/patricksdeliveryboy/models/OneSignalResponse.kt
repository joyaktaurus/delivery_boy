package app.com.patricksdeliveryboy.models

import com.fasterxml.jackson.annotation.JsonProperty

data class OneSignalResponse(

	@field:JsonProperty("androidNotificationId")
	val androidNotificationId: Int? = null,

	@field:JsonProperty("google.original_priority")
	val googleOriginalPriority: String? = null,

	@field:JsonProperty("alert")
	val alert: String? = null,

	@field:JsonProperty("google.sent_time")
	val googleSentTime: Long? = null,

	@field:JsonProperty("google.delivered_priority")
	val googleDeliveredPriority: String? = null,

	@field:JsonProperty("custom")
	val custom: String? = null,

	@field:JsonProperty("google.c.sender.id")
	val googleCSenderId: String? = null,

	@field:JsonProperty("google.ttl")
	val googleTtl: Int? = null,

	@field:JsonProperty("from")
	val from: String? = null,

	@field:JsonProperty("title")
	val title: String? = null,

	@field:JsonProperty("google.message_id")
	val googleMessageId: String? = null
)
