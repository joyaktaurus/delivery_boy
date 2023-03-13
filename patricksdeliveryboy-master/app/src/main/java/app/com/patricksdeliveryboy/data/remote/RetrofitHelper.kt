package app.com.patricksdeliveryboy.data.remote

import app.com.patricksdeliveryboy.utility.Validator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {


    private val retrofit = Retrofit.Builder()
        .baseUrl("http://35.184.51.220:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideClient())
        .build()


    private fun provideClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder().addInterceptor {
        val newRequest: Request = it.request().newBuilder()
            .addHeader("Authorization", "Bearer ${Validator.TOKEN}")
            .build()
        it.proceed(newRequest)
    }.addInterceptor(httpLoggingInterceptor).build() }

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}