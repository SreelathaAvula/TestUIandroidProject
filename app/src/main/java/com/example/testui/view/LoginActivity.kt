package com.example.testui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import java.util.concurrent.TimeUnit
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.testui.R
import com.example.testui.databinding.ActivityLoginBinding
import com.example.testui.model.UserInfo
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityLoginBinding
    private lateinit var databaseReference: DatabaseReference
    private var isOtp = false
    private val auth = FirebaseAuth.getInstance()
    var otpId = ""

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://testui-authentication-default-rtdb.firebaseio.com/")
        setClickListener()
        focusValidation()
        setButtonVisible()
        binding.tvGenerateOtp.setOnClickListener {
            var number = binding.etFragmentPhoneEditText.text.toString()
            if (number.isNotEmpty()) {
                if (number.length == 10)  {
                    validateNumberWithFireBase()

                } else {
                    Toast.makeText(this, "enter  10 digits", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "number should not be null", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btLoginOtp.setOnClickListener {
            if (binding.etFragmentPhoneEditText.text.toString() != "" && binding.etOtp.text.toString() != "") {
                val enteredOtp = binding.etOtp.text.toString()
                val credential = PhoneAuthProvider.getCredential(otpId, enteredOtp)
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(
                    this,
                    "phone number and password can not be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun validateNumberWithFireBase() {
        var number=binding.etFragmentPhoneEditText.text.toString()
        databaseReference.child("Users").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children){
                    val user=userSnapshot.getValue(UserInfo::class.java)
                    if (user!=null && user.phoneNumber == number){
                        number = "+91$number"
                        val options=PhoneAuthOptions.newBuilder(auth)
                            .setPhoneNumber(number)
                            .setActivity(this@LoginActivity)
                            .setTimeout(60L,TimeUnit.SECONDS)
                            .setCallbacks(Callbacks)
                            .build()
                        PhoneAuthProvider.verifyPhoneNumber(options)

                    }
                    if (user==null){
                        Toast.makeText(this@LoginActivity,"user is empty in the database",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@LoginActivity,"user not registered",Toast.LENGTH_SHORT).show()
                        binding.etFragmentPhoneEditText.text?.clear()
                        binding.etOtp.text?.clear()
                        binding.etTilPhoneNumber.error="number not registered"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LoginActivity,"${error.message}",Toast.LENGTH_SHORT).show()
            }

        })
    }

    val Callbacks =
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
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private fun setClickListener() {
        binding.apply {
            btPhone.setOnClickListener(this@LoginActivity)
            tvSignup.setOnClickListener(this@LoginActivity)
            ivClose.setOnClickListener(this@LoginActivity)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btPhone -> {
                if (isOtp) {
                    setMailVisibility()
                } else {
                    setOtpVisibility()
                }
            }
            R.id.tvSignup -> startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            R.id.ivClose -> redirectToFlashPage()
        }
    }

    private fun focusValidation() {
        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.etEmail.text.toString() == "") {
                    Log.i(TAG, "focusValidation: email empty")
                    binding.etLoginEmailContainer.error = getString(R.string.empty_email_text)
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
                    binding.etLoginEmailContainer.error = getString(R.string.invalid_mail_text)
                } else {
                    binding.etLoginEmailContainer.error = null
                    setEditTextListeners()
                }
            } else {
                binding.etLoginEmailContainer.error = null
                binding.etLoginPasswordContainer.error = null
                binding.etLoginEmailContainer.helperText = getString(R.string.required)
            }
        }
        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (binding.etPassword.text.toString() != "") {
                    binding.etLoginPasswordContainer.error = null
                } else {
                    binding.etLoginPasswordContainer.helperText = getString(R.string.required)
                }
            }
        }
    }

    private fun redirectToHomePage() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        databaseReference.child("Users").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(UserInfo::class.java)
                    if (user != null && user.email == email && user.password == password) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        Toast.makeText(
                            this@LoginActivity,
                            "redirect to main page or homepage",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else {
                        binding.etEmail.text?.clear()
                        binding.etPassword.text?.clear()
                        binding.etLoginEmailContainer.error = getString(R.string.invalidEmailText)
                        binding.etLoginPasswordContainer.error =
                            getString(R.string.invalidPasswordText)
                        binding.btEmailLogin.isEnabled = false
                        binding.btEmailLogin.isClickable = false
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i(TAG, "onCancelled:  database error")
            }
        })
    }

    private val loginTextWatcher = object : android.text.TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            setButtonVisible()
            Log.i(TAG, "onTextChanged: set button")
        }

        override fun afterTextChanged(p0: Editable?) {
            setButtonVisible()
        }
    }

    private fun setEditTextListeners() {
        binding.etEmail.addTextChangedListener(loginTextWatcher)
        binding.etPassword.addTextChangedListener(loginTextWatcher)
    }

    private fun setButtonVisible() {
        if ((Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
                .matches()) && (binding.etPassword.text.toString() != "")
        ) {
            binding.btEmailLogin.setBackgroundColor(resources.getColor(R.color.brandColor))
            binding.btEmailLogin.setOnClickListener {
                redirectToHomePage()
            }
        } else {
            binding.btEmailLogin.setBackgroundColor(resources.getColor(R.color.primaryWhite))
            binding.btEmailLogin.isClickable=false
        }
    }

    private fun redirectToFlashPage() {
        binding.etEmail.text?.clear()
        binding.etPassword.text?.clear()
        binding.etLoginEmailContainer.error = null
        binding.etLoginPasswordContainer.error = null
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                binding.etFragmentPhoneEditText.text?.clear()
                binding.etOtp.text?.clear()
            } else {
                Toast.makeText(this, "Verification failed", Toast.LENGTH_SHORT).show()
                binding.etFragmentPhoneEditText.text?.clear()
                binding.etOtp.text?.clear()

            }
        }
    }

    private fun setOtpVisibility() {
        with(binding) {
            countryCode.visibility = View.VISIBLE
            etTilPhoneNumber.visibility = View.VISIBLE
            tvGenerateOtp.visibility = View.VISIBLE
            etOtpContainer.visibility = View.VISIBLE
            etOtp.visibility = View.VISIBLE
            btLoginOtp.visibility = View.VISIBLE
            etLoginEmailContainer.visibility = View.INVISIBLE
            etLoginPasswordContainer.visibility = View.INVISIBLE
            btEmailLogin.visibility = View.INVISIBLE
            tvForgotText.visibility = View.INVISIBLE
            btPhone.setText(R.string.log_in_with_email_text)
            btPhone.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
        isOtp = true
    }

    private fun setMailVisibility() {
        with(binding) {
            countryCode.visibility = View.INVISIBLE

            etTilPhoneNumber.visibility = View.INVISIBLE
            tvGenerateOtp.visibility = View.INVISIBLE
            etOtpContainer.visibility = View.INVISIBLE
            btLoginOtp.visibility = View.INVISIBLE
            btPhone.setText(R.string.phoneText)
            btPhone.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_mobile), null, null, null
            )
            etLoginEmailContainer.visibility = View.VISIBLE
            etLoginPasswordContainer.visibility = View.VISIBLE
            btEmailLogin.visibility = View.VISIBLE
            tvForgotText.visibility = View.VISIBLE
        }
        isOtp = false

    }
}


