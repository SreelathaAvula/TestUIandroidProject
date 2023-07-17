package com.example.testui.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.testui.R
import com.example.testui.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.logout.setOnClickListener {
            startActivity(Intent(requireContext(),LoginActivity::class.java))
            Toast.makeText(requireContext(), getString(R.string.logout_text), Toast.LENGTH_SHORT).show()

        }
         return view
    }
}