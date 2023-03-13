package app.com.patricksdeliveryboy.di

import app.com.patricksdeliveryboy.APIEndpoints.BASE_URL
import app.com.patricksdeliveryboy.data.remote.ApiServices
import app.com.patricksdeliveryboy.data.remote.ExceptionInterceptor
import app.com.patricksdeliveryboy.utility.Validator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRemoteApiService(client: OkHttpClient) :ApiServices{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder().addInterceptor{
            val newRequest : Request = it.request().newBuilder()
                .addHeader("Authorization", "Bearer ${Validator.TOKEN}")
                .build()
            it.proceed(newRequest)
        }
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ExceptionInterceptor())
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()

    @Provides
    @Singleton
    fun provideLogging(): HttpLoggingInterceptor{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return  logging
    }
}