package com.example.testui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testui.R
import com.example.testui.adapter.UserAdapter
import com.example.testui.model.UserDetails
import com.example.testui.network.UsersApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.testui.model.UserDetails.Result


class UsersActivity : AppCompatActivity() {
    val TAG=UsersActivity::class.java.simpleName
    lateinit var  userAdapter:UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        getData()
    }
    private fun getData() {
        UsersApiClient().apiService.getUserData(5).enqueue(object : Callback<UserDetails>{
            override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                val recycleList:RecyclerView=findViewById(R.id.recycleList)
                if (response.isSuccessful){
                    val userDetails: UserDetails? = response.body()
                    val outPut=userDetails?.results
                 /*   val results: ArrayList<UserDetails.Result>? = userDetails.results*/
                    if(outPut != null){
                        userAdapter = UserAdapter() // Set the userAdapter again

                        recycleList.adapter=userAdapter
                        recycleList.layoutManager=LinearLayoutManager(this@UsersActivity)
                        recycleList.adapter?.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }
        })

    }
}