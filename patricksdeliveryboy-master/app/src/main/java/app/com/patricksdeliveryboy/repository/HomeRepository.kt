package app.com.patricksdeliveryboy.repository

import app.com.patricksdeliveryboy.data.remote.ApiServices
import app.com.patricksdeliveryboy.models.*

import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiServices: ApiServices) {

    suspend fun getProfile() : ProfileResponseModel =
        apiServices.getProfile()
    suspend fun getDashBoardDetail() : DashboardResponse =
        apiServices.getDashBoardDetails()

    suspend fun updateDeliveryBoyStatus(deliveryBoyStatus: PostUpdateDeliveryBoyStatus): UpdateDutyStatusResponseModel =
        apiServices.updateDutyStatus(deliveryBoyStatus)

}