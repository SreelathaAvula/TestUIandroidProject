package com.example.testui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testui.R
import com.example.testui.model.UserDetails
import com.example.testui.network.UsersApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersActivity : AppCompatActivity() {
    val TAG=UsersActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        getData()
    }

    private fun getData() {
        UsersApiClient().apiService.getUserData(5).enqueue(object : Callback<UserDetails>{
            override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                if (response.isSuccessful){
                    response.body().let {
                        Log.i(TAG, "onResponse:  ${response.body().toString()}")
                    }
                }
            }
            override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }

        })

    }
}