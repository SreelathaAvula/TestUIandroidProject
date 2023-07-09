package com.example.testui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testui.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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
        startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
    }

    private fun openMobileAuthenticationActivity() {
        startActivity(Intent(this@LoginActivity, MobileAuthenticationActivity::class.java))
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