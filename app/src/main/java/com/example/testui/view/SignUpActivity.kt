package com.example.testui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    companion object {
        private val TAG = SignUpActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        focusChangeValidation()
        binding.ivClose.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        }

        binding.checkbox.setOnClickListener {
            if (binding.checkbox.isChecked) {
                Toast.makeText(this, "checkbox checked accepted terms and conditions", Toast.LENGTH_SHORT).show()
                checkBox = true
            }
            else{
                checkBox = false
                Toast.makeText(this, "please accept terms and conditions ...", Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnSignUpContinue.setOnClickListener {
            if (firstName && phone && email && password && confirmPassword && checkBox) {
                saveToDatabase()
            }
            else{
                Toast.makeText(this@SignUpActivity,"check all the details correctly ",Toast.LENGTH_SHORT).show()
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
            Log.i(TAG, "saveToDatabase: ")
            Toast.makeText(this@SignUpActivity,"Successfully added  to the database",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))

            binding.apply {
                etFirstName.text = null
                etSecondName.text = null
                etEmail.text = null
                etPassword.text = null
                etConfirmPassword.text = null
                checkbox.isChecked = false
                tilFirstName.helperText = "Required"
                tilEmail.helperText = "Required"
                tilPhoneNumber.helperText = "Required"
                tilPassword.helperText = "Required"
                tilConfirmPassword.helperText = "Required"
            }
        }.addOnFailureListener{
            Log.i(TAG, " failed to saveToDatabase: ")
            Toast.makeText(this@SignUpActivity,"Failed added  to the database",Toast.LENGTH_SHORT).show()
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
            binding.tilFirstName.helperText = "name can not be empty"
        } else {
            binding.tilFirstName.helperText = null
            if (binding.etFirstName.text.toString() == "") {
                binding.tilFirstName.helperText = " name can not be Empty!!!"
            } else {
                firstName = true
                binding.tilFirstName.helperText = null
            }
        }

    }
    private fun emailFocusValidation(hasFocus: Boolean) {
        if (hasFocus) {
            binding.tilEmail.helperText = "email should match with pattern"

        } else {
            binding.tilEmail.helperText = null
            if (binding.etEmail.text.toString() == "") {
                binding.tilEmail.helperText = "Email can not be Empty!!!"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
                    .matches()
            ) {
                binding.tilEmail.helperText = "Invalid mail address"
            } else {
                email = true
            }
        }


    }

    private fun phoneNumberFocusValidation(hasFocus: Boolean) {
        if (hasFocus) {
            binding.tilPhoneNumber.helperText = if (binding.etPhone.text.toString().isEmpty()) "mobile should contain 10 digits" else null
        } else {
            binding.tilPhoneNumber.helperText = null
            if (binding.etPhone.text.toString() == "") {
                binding.tilPhoneNumber.helperText = "mobile number can not be empty!!!"
            } else if (binding.etPhone.text.toString().length < 10) {
                binding.tilPhoneNumber.helperText = "should contain 10 digits!!!1"
            } else if (binding.etPhone.text.toString().length > 10) {
                binding.tilPhoneNumber.helperText =
                    "No More!!! Mobile can take only 1O digits !!!"
            } else {
                phone = true
            }
        }

    }

    private fun passwordFocusValidation(hasFocus: Boolean) {
        if (hasFocus) {
            binding.tilPassword.helperText = "password must contain 15 characters"
        } else {
            binding.tilPassword.helperText = null
            if (binding.etPassword.text.toString() == "") {
                binding.tilPassword.helperText = "Password  can not be empty!!!"
            } else if (binding.etPassword.text.toString().length < 15) {
                binding.tilPassword.helperText = "should contain 15 alpha Numeric values!!!"
            } else if (binding.etPassword.text.toString().length == 15) {
                if (!binding.etPassword.text.toString().matches(".*[A-Z].*".toRegex())) {
                    binding.tilPassword.helperText = "Must contain 1 Upper Case Character"
                }
                if (!binding.etPassword.text.toString().matches(".*[a-z].*".toRegex())) {
                    binding.tilPassword.helperText = "Must contain 1 lower Case Character"
                }
                if (binding.etPassword.text.toString()
                        .matches(".*[.,)(%!?*/{}-].*".toRegex())
                ) {
                    binding.tilPassword.helperText =
                        "can not contain .,)(%!?*/{}- these symbols in your password"
                }
                if (!binding.etPassword.text.toString().matches(".*[@#$&^+=].*".toRegex())) {
                    binding.tilPassword.helperText =
                        "Must contain 1  special Symbols from this list @#\$&^+="
                } else {
                    password = true
                }
            }
        }
    }

    private fun confirmPasswordValidation(hasFocus: Boolean) {
        if (hasFocus) {
                binding.tilConfirmPassword.helperText = "confirm password should match with password"

        } else {
            binding.tilConfirmPassword.helperText = null
            if (binding.etPassword.text.toString()!=(binding.etConfirmPassword.text.toString())
            ) {
                binding.tilConfirmPassword.helperText =
                    "confirm password is not matching  with password"
            } else {
                confirmPassword = true
                binding.tilConfirmPassword.helperText =null
            }
        }
    }
}