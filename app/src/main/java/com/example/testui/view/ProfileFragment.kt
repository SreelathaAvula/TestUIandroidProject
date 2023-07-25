package com.example.testui.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testui.R
import com.example.testui.databinding.FragmentProfileBinding
import com.example.testui.session.OtpLoginSession
import com.example.testui.session.UserSession
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var userSession: UserSession
    private lateinit var session: OtpLoginSession

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        userSession = UserSession(requireContext())
        session = OtpLoginSession(requireContext())
        userSession.createLoginSession()
        session.createOtpLoginSession()
        binding.logout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
                .setMessage("Do you want to logout!!")
                .setTitle("Alert!")
            builder.setPositiveButton("Yes") { _, _ ->
                userSession.removeSession()
                session.removeOtpSession()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                Toast.makeText(
                    requireContext(),
                    getString(R.string.logout_text),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val dialogView = builder.create()
            dialogView.show()
        }

        binding.btnDeleteAccount.setOnClickListener {
            Log.i("profile fragment", "delete button clicked: ")
            val deleteBuilder = AlertDialog.Builder(requireContext())
                .setMessage("Do you want to Delete your account!!")
                .setTitle("Alert!")
            deleteBuilder.setPositiveButton("Yes") { _, _ ->
                Log.i("profile fragment", "yes clicked")
                val user = FirebaseAuth.getInstance().currentUser
                    Log.i("profile fragment user is", "$user")
                    user?.delete()?.addOnCompleteListener {
                        Log.i("profile fragment user is", " delete option")
                        if (it.isSuccessful) {
                            Log.i("profile fragment user is", " task is successful")
                            startActivity(Intent(requireContext(), LoginActivity::class.java))
                           // FirebaseAuth.getInstance().signOut()
                            Log.i("profile fragment user is", " deleted successfully from firebase")
                        }
                    }?.addOnFailureListener {
                        Log.i(
                            "profile fragment delete option exception",
                            "onCreateView: ${it.message}"
                        )
                    }
            }
            deleteBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            deleteBuilder.create().show()
        }

        return view
    }
}