package com.example.testui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testui.R
import com.example.testui.databinding.ActivitySignUpBinding
import com.example.testui.model.UserInfo
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    lateinit var firebaseDatabase:FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    var firstName: Boolean = false
    var password: Boolean = false
    var phone: Boolean = false
    var email: Boolean = false
    var confirmPassword: Boolean = false
    var checkBox:Boolean=false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        focusChangeValidation()
        binding.ivClose.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        }
        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(
                    this,
                    getString(R.string.checked_text),
                    Toast.LENGTH_SHORT
                ).show()
                checkBox = true
            } else {
                checkBox = false
                Toast.makeText(this, getString(R.string.unchecked_text), Toast.LENGTH_SHORT)
                    .show()
            }
        }


        binding.btnSignUpContinue.setOnClickListener {
            if (firstName && phone && email && password && confirmPassword && checkBox) {
                saveToDatabase()
            }
            else{
                Toast.makeText(this@SignUpActivity,getString(R.string.signup_error_text),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveToDatabase() {

        firebaseDatabase=FirebaseDatabase.getInstance()
        databaseReference=firebaseDatabase.getReference("Users")
        val firstName=binding.etFirstName.text.toString().trim()
        val lastName=binding.etSecondName?.text.toString().trim()
        val email=binding.etEmail?.text.toString().trim()
        val password=binding.etPassword?.text.toString().trim()
        val phone=binding.etPhone?.text.toString()


        val user=UserInfo(firstName,lastName,email,password, phone)
        databaseReference.child(firstName).setValue(user).addOnSuccessListener {
            Toast.makeText(this@SignUpActivity,getString(R.string.signup_success_datbase_text),Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))

            binding.apply {
                etFirstName.text = null
                etSecondName.text = null
                etEmail.text = null
                etPassword.text = null
                etConfirmPassword.text = null
                checkbox.isChecked = false
                tilFirstName.helperText =getString(R.string.required)
                tilEmail.helperText = getString(R.string.required)
                tilPhoneNumber.helperText = getString(R.string.required)
                tilPassword.helperText = getString(R.string.required)
                tilConfirmPassword.helperText = getString(R.string.required)
            }
        }.addOnFailureListener{
            Toast.makeText(this@SignUpActivity,getString(R.string.database_failed_text),Toast.LENGTH_SHORT).show()
        }

    }

    private fun focusChangeValidation() {

        binding.etFirstName.setOnFocusChangeListener { v, hasFocus ->
            firstNmeFocus(hasFocus)
        }

        binding.etEmail.setOnFocusChangeListener { v, hasFocus ->
            emailFocusValidation(hasFocus)

        }

        binding.etPhone.setOnFocusChangeListener { v, hasFocus ->
            phoneNumberFocusValidation(hasFocus)

        }
        binding.etPassword.setOnFocusChangeListener { v, hasFocus ->
            passwordFocusValidation(hasFocus)

        }
        binding.etConfirmPassword.setOnFocusChangeListener { v, hasFocus ->
            confirmPasswordValidation(hasFocus)

        }
    }


    private fun firstNmeFocus(hasFocus: Boolean) {
        if (hasFocus) {
        } else {
            binding.tilFirstName.helperText = null
            if (binding.etFirstName.text.toString() == "") {
                binding.tilFirstName.error = " name can not be Empty!!!"
            } else {
                firstName = true
                binding.tilFirstName.helperText = null
            }
        }

    }
    private fun emailFocusValidation(hasFocus: Boolean) {
        if (hasFocus) {

        } else {
            binding.tilEmail.helperText = null
            if (binding.etEmail.text.toString() == "") {
                binding.tilEmail.error = getString(R.string.empty_email_text)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
                    .matches()
            ) {
                binding.tilEmail.helperText = getString(R.string.invalid_mail_text)
            } else {
                email = true
            }
        }
    }

    private fun phoneNumberFocusValidation(hasFocus: Boolean) {
        if (hasFocus) {
        } else {
            binding.tilPhoneNumber.helperText = null
            if (binding.etPhone.text.toString() == "") {
                binding.tilPhoneNumber.error = getString(R.string.mobilr_empty_text)
            } else if (binding.etPhone.text.toString().length < 10) {
                binding.tilPhoneNumber.error = getString(R.string.mobile_10_digit_text)
            } else if (binding.etPhone.text.toString().length > 10) {
                binding.tilPhoneNumber.error =
                    getString(R.string.no_more_text)
            } else {
                phone = true
            }
        }

    }

    private fun passwordFocusValidation(hasFocus: Boolean) {
        if (hasFocus) {
        } else {
            binding.tilPassword.helperText = null
            if (binding.etPassword.text.toString() == "") {
                binding.tilPassword.error = getString(R.string.password_empty_text)
            } else if (binding.etPassword.text.toString().length < 15) {
                binding.tilPassword.error = getString(R.string.emil_text)
            } else if (binding.etPassword.text.toString().length == 15) {
                if (!binding.etPassword.text.toString().matches(".*[A-Z].*".toRegex())) {
                    binding.tilPassword.error = getString(R.string.upper_case_text)
                }
                if (!binding.etPassword.text.toString().matches(".*[a-z].*".toRegex())) {
                    binding.tilPassword.error = getString(R.string.lower_case_text)
                }
                if (binding.etPassword.text.toString()
                        .matches(".*[.,)(%!?*/{}-].*".toRegex())
                ) {
                    binding.tilPassword.error =
                        getString(R.string.not_special_symbol_text)
                }
                if (!binding.etPassword.text.toString().matches(".*[@#$&^+=].*".toRegex())) {
                    binding.tilPassword.error =
                        getString(R.string.special_symbol_text)
                } else {
                    password = true
                }
            }
        }
    }

    private fun confirmPasswordValidation(hasFocus: Boolean) {
        if (hasFocus) {

        } else {
            binding.tilConfirmPassword.helperText = null
            if (binding.etPassword.text.toString()!=(binding.etConfirmPassword.text.toString())
            ) {
                binding.tilConfirmPassword.helperText =
                    getString(R.string.password_not_matching_text)
            } else {
                confirmPassword = true
                binding.tilConfirmPassword.helperText =null
            }
        }
    }
}