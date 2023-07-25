package com.example.testui.session

import android.content.Context
import android.content.SharedPreferences

class OtpLoginSession(private val context: Context) {
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    companion object {
        private const val SHARED_PREF_NAME = "OtpSharedPreferences"
        private const val KEY_NUMBER= "userNumber"
    }

    fun createOtpLoginSession(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()

    }
    fun  saveOtpSession(phoneNumber: String){
        editor.putString(KEY_NUMBER,phoneNumber).apply()
    }
    fun getOtpSession():String?{
        return  sharedPreferences.getString(KEY_NUMBER,null)
    }
    fun removeOtpSession(){
        editor.remove(KEY_NUMBER).apply()
    }
}