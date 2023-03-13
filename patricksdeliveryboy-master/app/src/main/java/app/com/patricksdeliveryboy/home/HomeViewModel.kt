package app.com.patricksdeliveryboy.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.com.patricksdeliveryboy.data.remote.ApiServices
import app.com.patricksdeliveryboy.data.remote.RetrofitHelper
import app.com.patricksdeliveryboy.models.*
import app.com.patricksdeliveryboy.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    var showLoading = MutableLiveData<Boolean>()

    fun  getProfileDetails(onSuccess: (data : ProfileResponseModel) -> Unit, onError: () ->Unit){
        viewModelScope.launch {
            try {
                val response = repository.getProfile()
                onSuccess(response)
            } catch (e: Exception) {
                e.printStackTrace()
                onError()
            }
        }

    }


    fun  getDashboardDetails(onSuccess: (data : DashboardResponse) -> Unit, onError: () ->Unit){
        viewModelScope.launch {
            try {
                val response = repository.getDashBoardDetail()
                onSuccess(response)
            } catch (e: Exception) {
                e.printStackTrace()
                onError()
            }
        }

    }
    fun updateDeliveryBoyStatus(postUpdateDeliveryBoyStatus: PostUpdateDeliveryBoyStatus,  onSuccess: (data : UpdateDutyStatusResponseModel) -> Unit, onError: () ->Unit) {
        viewModelScope.launch {
            try {
                val response = repository.updateDeliveryBoyStatus(postUpdateDeliveryBoyStatus)
                onSuccess(response)
            } catch (e: Exception) {
                e.printStackTrace()
                onError()
            }
        }
    }


}