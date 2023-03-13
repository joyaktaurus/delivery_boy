package app.com.patricksdeliveryboy.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.com.patricksdeliveryboy.data.remote.ApiServices
import app.com.patricksdeliveryboy.data.remote.RetrofitHelper
import app.com.patricksdeliveryboy.models.ProfileResponseModel
import app.com.patricksdeliveryboy.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    var showLoading = MutableLiveData<Boolean>()
    var profileResponseLiveData = MutableLiveData<ProfileResponseModel>()

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


}