package com.example.testui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.testui.MainActivity
import com.example.testui.databinding.ActivityMobileAuthenticationBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.TimeUnit

class MobileAuthenticationActivity : AppCompatActivity() {
    val TAG = MobileAuthenticationActivity::class.java.simpleName

    private lateinit var binding: ActivityMobileAuthenticationBinding
    val auth = FirebaseAuth.getInstance()
    var otpId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMobileAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etOtp.visibility= View.GONE
        binding.btverify.visibility= View.GONE
        binding.progressBar.visibility=View.GONE

        binding.btOtp.setOnClickListener {
            var number = binding.etPhoneNumber.text.toString()
            if (number.isNotEmpty()) {
                if (number.length == 10) {
                    number = "+91$number"

                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(callbacks)
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                } else {
                    Toast.makeText(this,"enter only 10 digits",Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this,"number should not be null",Toast.LENGTH_SHORT).show()
            }
            binding.etOtp.visibility= View.VISIBLE
            binding.btverify.visibility= View.VISIBLE
        }

        binding.btverify.setOnClickListener {
            binding.progressBar.visibility=View.VISIBLE

            val enterdOtp = binding.etOtp.text.toString()
            val credential=PhoneAuthProvider.getCredential(otpId,enterdOtp)
            signInWithPhoneAuthCredential(credential)
        }
        binding.ivClose.setOnClickListener {
            startActivity(Intent(this@MobileAuthenticationActivity, MainActivity::class.java))
        }
    }
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            Log.i(TAG, "onCodeSent: ")
            otpId = verificationId
        }
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.i(TAG, "onVerificationCompleted: ")
            signInWithPhoneAuthCredential(credential)
        }
        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@MobileAuthenticationActivity, e.message, Toast.LENGTH_SHORT).show()
        }
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                binding.progressBar.visibility=View.GONE

                startActivity(Intent(this@MobileAuthenticationActivity, HomeActivity::class.java))
                binding.etOtp.visibility= View.GONE
                binding.btverify.visibility= View.GONE
            } else {
                Toast.makeText(this@MobileAuthenticationActivity, "Verification failed", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility=View.GONE

            }

        }
    }
}


