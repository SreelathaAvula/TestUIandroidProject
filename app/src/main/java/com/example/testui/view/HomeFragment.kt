package com.example.testui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testui.adapter.UserAdapter
import com.example.testui.databinding.FragmentHomeBinding
import com.example.testui.model.ResultsItem
import com.example.testui.viewmodel.UsersViewModel

class HomeFragment : Fragment() {
    val TAG = HomeFragment::class.java.simpleName
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: UsersViewModel
    lateinit var userAdapter: UserAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
        binding.recycleList.layoutManager = LinearLayoutManager(requireContext())
        userAdapter = UserAdapter()
        binding.recycleList.adapter = userAdapter
        viewModel.getAllUserDetails()
        binding.swipeContainer.setOnRefreshListener {
            viewModel.getAllUserDetails()
        }
        viewModel.userDetailsLiveData.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            if (it != null) {
                userAdapter.setUserData(it.results as List<ResultsItem>)
                userAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
                binding.swipeContainer.isRefreshing=false
                Toast.makeText(requireContext(), "Successfully get the data", Toast.LENGTH_SHORT).show()

            }
            else{
                binding.progressBar.visibility = View.GONE
                binding.swipeContainer.isRefreshing=false
                Toast.makeText(requireContext(), "Failed to get data", Toast.LENGTH_SHORT).show()

            }
        })
        return view
    }
}
