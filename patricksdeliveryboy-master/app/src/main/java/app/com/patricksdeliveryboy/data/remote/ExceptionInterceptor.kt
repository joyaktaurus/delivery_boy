package app.com.patricksdeliveryboy.data.remote

import com.inmenzo.patrics.exception.UnauthorizedException
import okhttp3.Interceptor
import okhttp3.Response

class ExceptionInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
     //   val request = chain.request().newBuilder().addHeader("Connection", "close").build()
        val response = chain.proceed(chain.request());
        if (response.code == 401){
            throw UnauthorizedException()
        }
        return response
    }
}