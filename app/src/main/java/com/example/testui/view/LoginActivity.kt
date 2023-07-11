package com.example.testui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.testui.R
import com.example.testui.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding:ActivityLoginBinding
    companion object {
        private  val TAG = LoginActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setClickListener()
    }
    private fun setClickListener() {
        binding.apply {
            btEmailLogin.setOnClickListener(this@LoginActivity)
            btPhone.setOnClickListener(this@LoginActivity)
            tvSignup.setOnClickListener(this@LoginActivity)
            ivClose.setOnClickListener(this@LoginActivity)
        }
    }
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btEmailLogin->reDirectToMainActivity()
            R.id.btPhone->openMobileAuthenticationActivity()
            R.id.tvSignup->reDirectToSignupPge()
            R.id.ivClose->redirectToFlashPage()
        }
    }
    private fun reDirectToMainActivity() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }
    private fun reDirectToSignupPge() {
        startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
    }
    private fun openMobileAuthenticationActivity() {
        startActivity(Intent(this@LoginActivity, MobileAuthenticationActivity::class.java))
    }
    private fun redirectToFlashPage() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}