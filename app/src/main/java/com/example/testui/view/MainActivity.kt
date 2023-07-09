package com.example.testui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testui.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClick()
    }
    private fun setOnClick() {
            binding.homeButton.setOnClickListener {
                startActivity(Intent(this@MainActivity, UsersActivity::class.java))
            }
    }
}