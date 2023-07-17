package com.example.testui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.testui.R
import com.example.testui.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCliCkListener()
    }

    private fun setCliCkListener() {
        binding.apply {
            homeButton.setOnClickListener(this@MainActivity)
            profileButton.setOnClickListener(this@MainActivity)
        }
    }
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.homeButton->placeFragmentPage()
            R.id.profileButton->placeProfileFragmentPage()
        }
    }
    private fun placeFragmentPage() {
        var fragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentMainActivity, fragment).commit()
        binding.nameIcon.visibility = View.GONE
        binding.mailNotification.visibility = View.GONE
        binding.starNotify.visibility = View.GONE
        binding.imgNotification.visibility = View.GONE

    }
    private fun placeProfileFragmentPage() {
        var fragment = ProfileFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentMainActivity, fragment).commit()
       binding.nameIcon.visibility = View.GONE
       binding.mailNotification.visibility = View.GONE
        binding.starNotify.visibility = View.GONE
        binding.imgNotification.visibility = View.GONE
    }
}