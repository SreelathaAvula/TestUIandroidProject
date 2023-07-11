package com.example.testui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testui.model.FaithconxUser
import com.example.testui.model.UserDetails

class UsersViewModel:ViewModel() {
    val users=MutableLiveData<MutableList<FaithconxUser>>()
    fun getFaithConxUserDta(){
    }

}