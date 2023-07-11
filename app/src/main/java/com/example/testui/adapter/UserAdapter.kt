package com.example.testui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testui.R
import com.example.testui.model.UserDetails

class UserAdapter(var user :List<UserDetails.Result>) : Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.list_details, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return user.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val result: UserDetails.Result = user?.get(position) as UserDetails.Result

        holder.title.text =result.name.first
        holder.titleTwo.text = result.name.last
        holder.detailOne.text = result.location.city
        holder.detailTwo.text = result.location.country
        holder.detailThree.text =result.location.state
        holder.detailFour.text = result.location.postcode.toString()
        holder.connectionCount.text =result.dob.age.toString()
        holder.count.text = result.dob.date
    }
    class UserViewHolder(itemView: View) : ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val titleTwo: TextView = itemView.findViewById(R.id.SecondTitle)
      /*  val imageOne: ImageView = itemView.findViewById(R.id.imgProfileOne)
        val imageTwo: ImageView = itemView.findViewById(R.id.imgProfileTwo)*/
        val detailOne: TextView = itemView.findViewById((R.id.tvDetailOne))
        val detailTwo: TextView = itemView.findViewById((R.id.tvDetailTwo))
        val detailThree: TextView = itemView.findViewById((R.id.tvDetailThree))
        val detailFour: TextView = itemView.findViewById((R.id.tvDetailFour))
        val connectionCount: TextView = itemView.findViewById(R.id.tvConnectionCount)
        val count: TextView = itemView.findViewById(R.id.tvCount)
    }
}