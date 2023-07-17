package com.example.testui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.testui.R
import com.example.testui.databinding.ActivityLoginBinding
import com.example.testui.model.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityLoginBinding
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://testui-authentication-default-rtdb.firebaseio.com/")
        setClickListener()
        textChangeListener()

    }

    private fun textChangeListener() {
        binding.etEmail.addTextChangedListener {

        }
        TODO("Not yet implemented")
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
        when (view?.id) {

            R.id.btEmailLogin -> validateLoginEmail()
            R.id.btPhone -> openMobileAuthenticationActivity()
            R.id.tvSignup -> reDirectToSignupPge()
            R.id.ivClose -> redirectToFlashPage()
        }
    }

    private fun validateLoginEmail() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {

            databaseReference.child("Users").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(UserInfo::class.java)
                        if (user != null && user.email == email && user.password == password) {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            Toast.makeText(this@LoginActivity, getString(R.string.login_successful_text), Toast.LENGTH_SHORT).show()
                        }
                        else {

                            Toast.makeText(this@LoginActivity, getString(R.string.invalid_email_or_password), Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error occurred: " + databaseError.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })


        } else {
            Toast.makeText(this, getString(R.string.mail_passowrd_empty_text), Toast.LENGTH_SHORT)
                .show()
        }
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