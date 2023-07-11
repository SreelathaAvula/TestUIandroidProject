package com.example.testui.network

import com.example.testui.model.FaithconxUser
import com.example.testui.model.UserDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    @GET("/api/")
    fun getUserData(@Query("results") results : Int): Call<UserDetails>
    @GET("/api/")
    fun getUserDetails(@Query("results") results : Int): Call<FaithconxUser>

}