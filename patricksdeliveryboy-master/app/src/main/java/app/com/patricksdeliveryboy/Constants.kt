package app.com.patricksdeliveryboy

object APIEndpoints{


    const val BASE_URL= " http://34.93.247.52:8000"

    const val LOGIN = "delivery-boy-accounts/login"

    //Profile
    const val DELIVERY_BOY_PROFILE = "delivery-boy-accounts/profile"

    //Duty Status
    const val UPDATE_DUTY_STATUS = "delivery-boy-accounts/duty/status"

    //Shift Details
    const val SHIFT_DETAILS = "delivery-boy-accounts/shift/details"

    //Shift Details
    const val EARNED_DETAILS = "delivery-boy-accounts/earned/details"

    //Shift Details
    const val REQUEST_ORDER_DETAILS = "delivery-boy-orders/request/{reference_id}"

    //Order Accept Reject
    const val ORDER_ACCEPT_REJECT = "delivery-boy-orders/request/{reference_id}/status"

    //Ordered Item Details
    const val ORDERED_ITEM_DETAILS = "delivery-boy-orders/order/{reference_id}/product"

    //Dashboard
    const val DASHBOARD_DETAILS = "delivery-boy-accounts/dashboard"

    const val SOCKET_URL = "http://34.93.247.52:8000/deliveryboy-location"
}

class ReferenceIds{
    companion object{
        var ReferenceId = ""
    }
}


object PageUtils {
    const val PER_PAGE = 10
}
