package com.example.testui.network

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersApiClient {

    companion object {
        private const val BASE_URL = "https://randomuser.me/"
    }
    private val okhttpInterceptor = OkHttpProfilerInterceptor()
    private val client = OkHttpClient.Builder()
        .addInterceptor(okhttpInterceptor)
        .build()
//create an retrofit instance
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
    //creates an API service using the Retrofit instance.
    val apiService:UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}