package app.com.patricksdeliveryboy.di


import app.com.patricksdeliveryboy.APIEndpoints
import app.com.patricksdeliveryboy.utility.Validator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URI
import java.net.URISyntaxException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SocketModule {
    @Inject
    lateinit var socket: Socket
    @Provides
    @Singleton
    fun provideSocket(): Socket {
        var mSocket: Socket? = null
        try {
            val options = IO.Options.builder()
                .setPath("/deliveryboy-location")
                .setAuth(Collections.singletonMap("token", Validator.TOKEN))
                .build()
            mSocket = IO.socket(URI.create(APIEndpoints.SOCKET_URL), options)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
        return mSocket!!
    }
}