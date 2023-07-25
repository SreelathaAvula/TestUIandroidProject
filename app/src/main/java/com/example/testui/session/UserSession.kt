package com.example.testui.session

import android.content.Context
import android.content.SharedPreferences
import android.provider.ContactsContract.CommonDataKinds.Email

class UserSession(private  val context: Context) {
    private lateinit var sharedPreferences:SharedPreferences
    lateinit var editor:SharedPreferences.Editor
    companion object {
        private const val SHARED_PREF_NAME = "Signup Shared Preference"
        private const val KEY_EMAIL= "userEmail"
        private const val USER_IS_LOGGED_IN = "isLoggedIn"
    }

fun createLoginSession(){
    sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    editor=sharedPreferences.edit()

}
   fun  saveSession(email: String){
       editor.putString(KEY_EMAIL,email)
       editor.putBoolean(USER_IS_LOGGED_IN,true)
       editor.apply()


   }
    fun getSession():String?{
      return  sharedPreferences.getString(KEY_EMAIL,null)

   }
    fun removeSession(){
        editor.remove(KEY_EMAIL)
        editor.putBoolean(USER_IS_LOGGED_IN,false)
        editor.apply ()

    }

}