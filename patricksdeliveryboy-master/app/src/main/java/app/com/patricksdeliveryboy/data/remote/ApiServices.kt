package app.com.patricksdeliveryboy.data.remote


import app.com.patricksdeliveryboy.APIEndpoints.DASHBOARD_DETAILS
import app.com.patricksdeliveryboy.APIEndpoints.DELIVERY_BOY_PROFILE
import app.com.patricksdeliveryboy.APIEndpoints.EARNED_DETAILS
import app.com.patricksdeliveryboy.APIEndpoints.LOGIN
import app.com.patricksdeliveryboy.APIEndpoints.ORDERED_ITEM_DETAILS
import app.com.patricksdeliveryboy.APIEndpoints.ORDER_ACCEPT_REJECT
import app.com.patricksdeliveryboy.APIEndpoints.REQUEST_ORDER_DETAILS
import app.com.patricksdeliveryboy.APIEndpoints.SHIFT_DETAILS
import app.com.patricksdeliveryboy.APIEndpoints.UPDATE_DUTY_STATUS
import app.com.patricksdeliveryboy.PageUtils
import app.com.patricksdeliveryboy.models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @POST(LOGIN)
    suspend fun postLogin(@Body postLogin: PostLogin): LoginResponseModel

    @GET(DELIVERY_BOY_PROFILE)
    suspend fun getProfile(): ProfileResponseModel

    @POST(UPDATE_DUTY_STATUS)
    suspend fun updateDutyStatus(@Body postUpdateDeliveryBoyStatus: PostUpdateDeliveryBoyStatus): UpdateDutyStatusResponseModel

    @GET(SHIFT_DETAILS)
    suspend fun getShiftDetails(@Query("page") page:Int, @Query("limit") perPage:Int = PageUtils.PER_PAGE): ShiftDetailsResponse

    @GET(EARNED_DETAILS)
    suspend fun getEarnedDetails(@Query("page") page:Int, @Query("limit") perPage:Int = PageUtils.PER_PAGE): EarningDetailsResponse

    @GET(REQUEST_ORDER_DETAILS)
    suspend fun getOrderRequestDetails(@Path("reference_id") referenceId : String) : OrderRequestDetailsResponse

    @POST(ORDER_ACCEPT_REJECT)
    suspend fun updateAcceptRejectStatus(@Path("reference_id") referenceId : String, @Body postAcceptRejectStatus: PostAcceptRejectStatus) : OrderAcceptRejectResponse

    @GET(ORDERED_ITEM_DETAILS)
    suspend fun orderedItemDetails(@Path("reference_id") referenceId : String, @Query("sellerId") sellerId : String) : OrderedItemResponse

    @GET(DASHBOARD_DETAILS)
    suspend fun getDashBoardDetails(): DashboardResponse





}