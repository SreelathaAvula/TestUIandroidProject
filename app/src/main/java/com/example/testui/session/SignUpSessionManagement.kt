package com.example.testui.session

import android.content.Context
import android.content.SharedPreferences
import com.example.testui.model.UserInfo
import com.google.gson.Gson

class SignUpSessionManagement(private  val context: Context) {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    companion object {
        private const val SHARED_PREF_NAME = "Signup Shared Preference"
    }
    fun createSession(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()
    }
    fun saveSignupData(signupData:UserInfo) {

        val gson = Gson()
        val json = gson.toJson(signupData)
        editor.putString("signup_data", json).apply()
    }

    fun getSignupData(): UserInfo? {
        val gson = Gson()
        val json = sharedPreferences.getString("signup_data", null)
        return gson.fromJson(json, UserInfo::class.java)
    }
    fun clearSignupData() {
        sharedPreferences.edit().remove("signup_data").apply()
    }

}