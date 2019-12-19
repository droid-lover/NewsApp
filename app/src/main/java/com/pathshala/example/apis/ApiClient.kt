package com.assignment

import com.assignment.utils.Api
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Sachin.
 */

object ApiClient {

    private val TIMEOUT_CONNECT = 90
    private val TIMEOUT_READ = 90

    private var retrofit: APIs? = null


    val client: APIs
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(Api.BASE_URL_MOVIES)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .build().create(APIs::class.java)
            }
            return retrofit!!
        }

    private val okHttpClient: OkHttpClient
        get() {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
            builder.interceptors().add(logging)
            builder.interceptors().add(interceptor)
            builder.connectTimeout(TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
            builder.readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
            return builder.build()
        }


    private val interceptor: Interceptor
        get() = Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder().apply {
                header("client_version", "CLIENT_VERSION")
                header("client_name", "CLIENT_NAME")
                header("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                header("x-rapidapi-key", "mCwP32PFxUmshb1OwysSpUByMbXWp1QPsCZjsnW4JXJ5AA8zjn")
                method(original.method(), original.body())
            }.build()
            chain.proceed(request)
        }

}