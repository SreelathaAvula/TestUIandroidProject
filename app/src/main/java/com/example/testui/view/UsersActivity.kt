package com.example.testui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testui.adapter.UserAdapter
import com.example.testui.databinding.ActivityUsersBinding
import com.example.testui.model.ResultsItem
import com.example.testui.viewmodel.UsersViewModel


class UsersActivity : AppCompatActivity() {
    val TAG = UsersActivity::class.java.simpleName
    lateinit var binding: ActivityUsersBinding
    lateinit var viewModel: UsersViewModel
    lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding.recycleList.layoutManager = LinearLayoutManager(this)
        userAdapter=UserAdapter()
        binding.recycleList.adapter=userAdapter

        viewModel.userDetailsLiveData.observe(this, Observer{
            Log.d(TAG, "onCreate: $it")
            if (it != null) {
                userAdapter.setUserData(it.results as List<ResultsItem>)
                userAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getAllUserDetails()
    }
}


