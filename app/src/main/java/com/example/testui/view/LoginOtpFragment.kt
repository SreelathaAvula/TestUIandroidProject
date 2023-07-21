package com.example.testui.view

import android.content.Intent
import android.os.Bundle
import java.util.concurrent.TimeUnit
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testui.databinding.FragmentOtpLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider

class LoginOtpFragment : Fragment() {
    private lateinit var binding: FragmentOtpLoginBinding
    private val auth = FirebaseAuth.getInstance()
    var otpId = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentOtpLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.tvGenerateOtp.setOnClickListener {
            var number = binding.etFragmentPhoneEditText.text.toString()
            if (number.isNotEmpty()) {
                if (number.length == 10) {
                    number = "+91$number"
                    val callbacks =
                        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            override fun onCodeSent(
                                verificationId: String,
                                token: PhoneAuthProvider.ForceResendingToken
                            ) {
                                otpId = verificationId
                            }
                            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                                signInWithPhoneAuthCredential(credential)
                            }
                            override fun onVerificationFailed(e: FirebaseException) {
                                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(requireActivity())
                        .setCallbacks(callbacks)
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                } else {
                    Toast.makeText(requireContext(), "enter  10 digits", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "number should not be null", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btLoginOtp.setOnClickListener {
            if(binding.etFragmentPhoneEditText.text.toString() !="" && binding.etOtp.text.toString() !=""){
            val enteredOtp = binding.etOtp.text.toString()
            val credential = PhoneAuthProvider.getCredential(otpId, enteredOtp)
            signInWithPhoneAuthCredential(credential)
    }
            else{
                Toast.makeText(requireContext(), "phone number and password can not be empty", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                binding.etFragmentPhoneEditText.text?.clear()
                binding.etOtp.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Verification failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}