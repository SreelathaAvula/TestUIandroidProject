package com.example.testui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.testui.R
import com.example.testui.session.OtpLoginSession
import com.example.testui.session.UserSession
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var  userSession:UserSession
    private lateinit var  session:OtpLoginSession


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        userSession = UserSession(this)
        session=OtpLoginSession(this)
        Handler().postDelayed({
            checkUserStatus()
        },1000L)
    }

    private fun checkUserStatus() {
        userSession.createLoginSession()
        session.createOtpLoginSession()

       val userEmail = userSession.getSession()
        val userPhone=session.getOtpSession()
       // val user=FirebaseAuth.getInstance().currentUser
        if (userEmail !=null || userPhone !=null ){
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
        }
        else{
            startActivity(Intent(this,LoginActivity::class.java))
        }
        finish()
    }

}