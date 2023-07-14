package com.example.testui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testui.model.UserDetails
import com.example.testui.network.UsersApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel:ViewModel() {
    companion object{
        private val TAG =UsersViewModel::class.java.simpleName
    }

    val userDetailsLiveData= MutableLiveData<UserDetails>()
     fun getAllUserDetails() {
        UsersApiClient().apiService.getAllUserDetails(5).enqueue(object : Callback<UserDetails>{
            override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>){
                if (response.isSuccessful){
                    val userDetails= response.body()
                    Log.i(TAG, "onResponse: ${userDetails.toString()}")
                    userDetailsLiveData.postValue(userDetails!!)
                    Log.i(TAG, "onResponse:")
                }
            }
            override fun onFailure(call: Call<UserDetails>,t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }
        })
    }
}


