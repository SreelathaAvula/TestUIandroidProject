package com.example.testui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testui.databinding.ActivityMainBinding
import com.example.testui.view.MobileAuthenticationActivity
import com.example.testui.view.SignUpActivity
import com.example.testui.view.UsersActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding.tvSignup.setOnClickListener {
            openSignupPge()
        }
        binding.ivClose.setOnClickListener {

            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        binding.btPhone.setOnClickListener {
            openMobileAuthenticationActivity()
        }
    }

    private fun openSignupPge() {
        startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
    }

    private fun openMobileAuthenticationActivity() {
        startActivity(Intent(this@MainActivity, MobileAuthenticationActivity::class.java))
    }
    override fun onBackPressed() {
        if (isTaskRoot) {
            val intent = Intent(this,UsersActivity::class.java)
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }
}