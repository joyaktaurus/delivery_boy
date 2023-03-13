package app.com.patricksdeliveryboy.orderAccept

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.com.patricksdeliveryboy.models.OrderAcceptRejectResponse
import app.com.patricksdeliveryboy.models.OrderRequestDetailsResponse
import app.com.patricksdeliveryboy.models.OrderedItemResponse
import app.com.patricksdeliveryboy.models.PostAcceptRejectStatus
import app.com.patricksdeliveryboy.repository.OrderAcceptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderAcceptViewModel @Inject constructor(private val repository: OrderAcceptRepository) : ViewModel(){

    fun getOrderRequestDetails(referenceId: String, onSuccess:(data: OrderRequestDetailsResponse)->Unit, onError: () -> Unit){
        viewModelScope.launch {
            try {
                val response = repository.getOrderRequestDetails(referenceId)
                onSuccess(response)
            } catch (e: Exception) {
                e.printStackTrace()
                onError()
            }
        }
    }

    fun updateOrderAcceptRejectStatus(referenceId: String, postAcceptRejectStatus: PostAcceptRejectStatus, onSuccess: (data: OrderAcceptRejectResponse) -> Unit, onError: () -> Unit){
        viewModelScope.launch {
            try {
                val response = repository.updateOrderAcceptReject(referenceId,postAcceptRejectStatus)
                onSuccess(response)
            } catch (e: Exception) {
                e.printStackTrace()
                onError()
            }
        }
    }

    fun getOrderedItemDetails(referenceId: String, sellerId : String, onSuccess: (data: OrderedItemResponse) -> Unit, onError: () -> Unit){
        viewModelScope.launch {
            try {
                val response = repository.getOrderedItemDetails(referenceId,sellerId)
                onSuccess(response)
            } catch (e: Exception) {
                e.printStackTrace()
                onError()
            }
        }
    }
}