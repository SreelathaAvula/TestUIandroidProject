package com.example.testui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testui.databinding.ActivityMainBinding
import com.example.testui.view.SignUpActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding.tvSignup.setOnClickListener {
            openSignupPge()
        }
        binding.btPhone.setOnClickListener {
        }
    }

    private fun openSignupPge() {
        startActivity(Intent(this@MainActivity,SignUpActivity::class.java))
    }
}