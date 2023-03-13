package app.com.patricksdeliveryboy.repository

import app.com.patricksdeliveryboy.data.remote.ApiServices
import app.com.patricksdeliveryboy.models.Data
import app.com.patricksdeliveryboy.models.LoginResponseModel
import app.com.patricksdeliveryboy.models.PostLogin
import com.inmenzo.patrics.db.LoginDao
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val loginDao: LoginDao
) {

    suspend fun login(postLogin: PostLogin): LoginResponseModel =
        apiServices.postLogin(postLogin)

    suspend fun insertUser(user: Data) = loginDao.insertUser(user)

    suspend fun getUser() = loginDao.getUser()

}