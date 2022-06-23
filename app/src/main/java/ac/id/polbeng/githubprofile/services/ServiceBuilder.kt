package ac.id.polbeng.githubprofile.services

import ac.id.polbeng.githubprofile.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ac.id.polbeng.githubprofile.helpers.Config
import java.util.concurrent.TimeUnit
object ServiceBuilder {
    //create loggingInterceptor
    private val loggingInterceptor = if(BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
    //create okhttp client
    private val okHttp: OkHttpClient.Builder = OkHttpClient.Builder()
        .callTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
    //create retrofit builder
    private val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(Config.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
    //create retrofit instance
    private val retrofit: Retrofit = builder.build()
    fun <T> buildService(serviceType: Class<T>): T{

        return retrofit.create(serviceType)
    }
}