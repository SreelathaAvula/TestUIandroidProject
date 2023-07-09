package com.example.testui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testui.R
import com.example.testui.model.UserDetails
import com.example.testui.model.UserDetails.Result

class UserAdapter(val details: List<UserDetails.Result>) : Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.list_details, parent, false)
        return UserViewHolder(view)
    }
    override fun getItemCount(): Int {
        return details.size
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data:Result=details?.get(position) as Result
        holder.celltv.text=data.cell
        holder.dobTv.text=data.dob.age.toString()
        holder.emailTv.text=data.email
        holder.genderTv.text=data.gender
    }

    class UserViewHolder(itemView: View) : ViewHolder(itemView) {
        val celltv:TextView=itemView.findViewById(R.id.tvcell)
        val dobTv:TextView=itemView.findViewById(R.id.tvdob)
        val emailTv:TextView=itemView.findViewById(R.id.tvemail)
        val genderTv:TextView=itemView.findViewById(R.id.tvGender)
    }
}