package com.example.testui.view

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.testui.R
import com.example.testui.databinding.ActivitySignUpBinding
import com.example.testui.model.UserInfo
import com.example.testui.session.SignUpSessionManagement
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*


class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        private var TAG = SignUpActivity::class.java.simpleName
    }

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private var firstName: Boolean = false
    private var password: Boolean = false
    private var phone: Boolean = false
    private var email: Boolean = false
    private var checkBox: Boolean = false
    private var imageUri: Uri? = null
    private var downloadUrl: Task<Uri>? = null
    lateinit var signUpSession: SignUpSessionManagement
    private val auth = FirebaseAuth.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        signUpSession=SignUpSessionManagement(this)
        setClickListener()
        focusChangeValidation()
    }

    private fun setClickListener() {
        binding.apply {
            btnSignUpContinue.setOnClickListener(this@SignUpActivity)
            changeProfile.setOnClickListener(this@SignUpActivity)
            ivClose.setOnClickListener(this@SignUpActivity)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ivClose -> {
                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            }
            R.id.changeProfile -> {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "pick your image"), 22)
            }
            R.id.btnSignUpContinue -> {
                validateDetails()
            }
        }
    }
    private fun validateDetails() {
        Log.i(TAG, "submit: $firstName $email $phone $password ${imageUri.toString()} $checkBox")
        if (firstName && phone && email && password && checkBox ) {
            if(binding.etConfirmPassword.text.toString() == binding.etPassword.text.toString()){
                uploadImage()
            }
            else{
                binding.tilConfirmPassword.error=getString(R.string.confirmPasswordText)
            }
        } else {
            Toast.makeText(
                this@SignUpActivity,
                getString(R.string.signup_error_text),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun uploadImage() {
        if (imageUri != null) {
            val storageReference: StorageReference =
                FirebaseStorage.getInstance().reference.child(UUID.randomUUID().toString())
            storageReference.putFile(imageUri!!).addOnSuccessListener {
                saveToDatabase()
                Toast.makeText(this, "Image Uploaded..", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(this, "Fail to Upload Image..", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 22 && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                binding.profileImage.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveToDatabase() {
        Log.i(TAG, "saveToDatabase:$imageUri ")
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Users")
        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etSecondName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val phone = binding.etPhone.text.toString()
        val image = imageUri.toString()
        Log.i(TAG, "saveToDatabase: $image")
        signUpSession.createSession()
        val user = UserInfo(firstName, lastName, email, password, phone, image)
        signUpSession.saveSignupData(user)
        databaseReference.child(firstName).setValue(user).addOnSuccessListener {
            Log.i(TAG, "saveToDatabase:  success ")
            Toast.makeText(this@SignUpActivity, getString(R.string.signup_success_datbase_text), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))

            binding.apply {
                etFirstName.text = null
                etSecondName.text = null
                etEmail.text = null
                etPassword.text = null
                etConfirmPassword.text = null
                checkbox.isChecked = false
            }
        }.addOnFailureListener { Log.i(TAG, "saveToDatabase: failed")
            Toast.makeText(this@SignUpActivity, getString(R.string.database_failed_text), Toast.LENGTH_SHORT).show()
        }
    }

    private fun focusChangeValidation() {
        binding.etFirstName.setOnFocusChangeListener { _, hasFocus ->
            firstNmeFocus(hasFocus)
        }
        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            emailFocusValidation(hasFocus)
        }
        binding.etPhone.setOnFocusChangeListener { _, hasFocus ->
            phoneNumberFocusValidation(hasFocus)
        }
        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            passwordFocusValidation(hasFocus)
        }
        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            checkBoxValidation(isChecked)
        }
    }

    private fun checkBoxValidation(checked: Boolean) {
        if (checked) {
            Toast.makeText(this, getString(R.string.checked_text), Toast.LENGTH_SHORT).show()
            checkBox = true
        } else {
            Toast.makeText(this, getString(R.string.unchecked_text), Toast.LENGTH_SHORT).show()
        }
    }

    private fun firstNmeFocus(hasFocus: Boolean) {
        if (hasFocus) {
            binding.tilFirstName.helperText = getString(R.string.required)

        } else {
            binding.tilFirstName.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@SignUpActivity,R.color.red)))
            binding.tilFirstName.helperText = null
            if (binding.etFirstName.text.toString() == "") {
                binding.tilFirstName.error = " name can not be Empty!!!"

            }
            else if(binding.etFirstName.text.toString().length <3){
                binding.tilFirstName.error = " name should contain 3 letters"
            }
                else {
                firstName = true
                binding.tilFirstName.error = null
            }
        }
    }

    private fun emailFocusValidation(hasFocus: Boolean) {
        if (hasFocus) {
            binding.tilEmail.helperText = getString(R.string.required)
        } else {
            binding.tilFirstName.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@SignUpActivity,R.color.red)))
            binding.tilEmail.helperText = null
            if (binding.etEmail.text.toString() == "") {
                binding.tilEmail.error = getString(R.string.empty_email_text)
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
                    .matches()
            ) {
                binding.tilEmail.error = getString(R.string.invalid_mail_text)
            } else {
                email = true
                binding.tilEmail.error = null
            }
        }
    }

    private fun phoneNumberFocusValidation(hasFocus: Boolean) {

        if (hasFocus) {
            binding.tilPhoneNumber.helperText = getString(R.string.required)
        } else {
            binding.tilFirstName.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@SignUpActivity,R.color.red)))

            if (binding.etPhone.text.toString() == "") {
                binding.tilPhoneNumber.error = getString(R.string.mobilr_empty_text)
            } else if (binding.etPhone.text.toString().length < 10) {
                binding.tilPhoneNumber.error = getString(R.string.mobile_10_digit_text)
            } else if (binding.etPhone.text.toString().length > 10) {
                binding.tilPhoneNumber.error =
                    getString(R.string.no_more_text)
            } else {
                binding.tilPhoneNumber.error = null
                phone = true
            }
        }
    }

    private fun passwordFocusValidation(hasFocus: Boolean) {
        if (hasFocus) {
            binding.tilPassword.helperText = getString(R.string.required)

        } else {
            binding.tilFirstName.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@SignUpActivity,R.color.red)))

            if (binding.etPassword.text.toString() == "") {
                binding.tilPassword.error = getString(R.string.password_empty_text)
            }
            if (binding.etPassword.text.toString().length < 15) {
                binding.tilPassword.error = getString(R.string.password_text)
            }
            if (binding.etPassword.text.toString().length == 15) {
                if (!binding.etPassword.text.toString().matches(".*[A-Z].*".toRegex())) {
                    binding.tilPassword.error = getString(R.string.upper_case_text)
                }
                if (!binding.etPassword.text.toString().matches(".*[a-z].*".toRegex())) {
                    binding.tilPassword.error = getString(R.string.lower_case_text)
                }
                if (binding.etPassword.text.toString()
                        .matches(".*[.,)(%!?*/{}-].*".toRegex())
                ) {
                    binding.tilPassword.error = getString(R.string.not_special_symbol_text)
                }
                if (!binding.etPassword.text.toString().matches(".*[@#$&^+=].*".toRegex())) {
                    binding.tilPassword.error = getString(R.string.special_symbol_text)
                } else {
                    binding.tilPassword.error = null
                    password = true
                }
            }
        }
    }
}
