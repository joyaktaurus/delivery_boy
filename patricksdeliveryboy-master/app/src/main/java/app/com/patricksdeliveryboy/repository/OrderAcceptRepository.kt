package app.com.patricksdeliveryboy.repository

import app.com.patricksdeliveryboy.data.remote.ApiServices
import app.com.patricksdeliveryboy.models.OrderAcceptRejectResponse
import app.com.patricksdeliveryboy.models.OrderRequestDetailsResponse
import app.com.patricksdeliveryboy.models.OrderedItemResponse
import app.com.patricksdeliveryboy.models.PostAcceptRejectStatus
import javax.inject.Inject

class OrderAcceptRepository @Inject constructor(private val apiServices: ApiServices){

    suspend fun getOrderRequestDetails(referenceId : String) : OrderRequestDetailsResponse =
            apiServices.getOrderRequestDetails(referenceId)

    suspend fun updateOrderAcceptReject(referenceId : String, postAcceptRejectStatus: PostAcceptRejectStatus) : OrderAcceptRejectResponse =
            apiServices.updateAcceptRejectStatus(referenceId, postAcceptRejectStatus)

    suspend fun getOrderedItemDetails(referenceId : String, sellerId: String) : OrderedItemResponse =
            apiServices.orderedItemDetails(referenceId, sellerId)
}