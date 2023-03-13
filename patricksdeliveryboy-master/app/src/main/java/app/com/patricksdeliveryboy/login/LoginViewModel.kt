package app.com.patricksdeliveryboy.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.com.patricksdeliveryboy.data.remote.ApiServices
import app.com.patricksdeliveryboy.data.remote.RetrofitHelper
import app.com.patricksdeliveryboy.models.Data
import app.com.patricksdeliveryboy.models.LoginResponseModel
import app.com.patricksdeliveryboy.models.PostLogin
import app.com.patricksdeliveryboy.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    var showLoading = MutableLiveData<Boolean>()
    var dataEntered = MutableLiveData(true)
    private lateinit var loginResponse : LiveData<Data>

    fun subScribeResponse(): LiveData<Data> {
        viewModelScope.launch {
            loginResponse = repository.getUser()
        }
        return loginResponse
    }

    fun submitLogin(
        postLogin: PostLogin,
        onError: (message: String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.login(postLogin)
                if (!response.error!!){
                    repository.insertUser(response.data!!)
                } else {
                    onError(response.msg)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onError(e.message)
            }
        }
    }
}