package com.example.testui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testui.model.Result

class UsersViewModel:ViewModel() {
    val users=MutableLiveData<MutableList<Result>>()

}