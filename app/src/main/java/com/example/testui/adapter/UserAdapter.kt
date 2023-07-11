package com.example.testui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testui.R
import com.example.testui.model.FaithconxUser
import com.example.testui.model.ListData
import com.example.testui.model.UserDetails
import com.example.testui.model.UserDetails.Result

class UserAdapter : Adapter<UserAdapter.UserViewHolder>() {
    var myList = arrayListOf<FaithconxUser>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.list_details, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data: FaithconxUser = myList?.get(position) as FaithconxUser
        holder.title.text = data.title
        holder.titleTwo.text = data.subTitle
        val detailsData: List<ListData> = data.detailsData
        val listData: ListData = detailsData[position]
        holder.detailOne.text = listData.firstData
        holder.detailTwo.text = listData.secondData
        holder.detailThree.text = listData.thirdData
        holder.detailFour.text = listData.fourData
        holder.connectionCount.text = data.connectionCount
        holder.count.text = data.count

    }

    class UserViewHolder(itemView: View) : ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val titleTwo: TextView = itemView.findViewById(R.id.SecondTitle)
        val imageOne: ImageView = itemView.findViewById(R.id.imgProfileOne)
        val imageTwo: ImageView = itemView.findViewById(R.id.imgProfileTwo)
        val detailOne: TextView = itemView.findViewById((R.id.tvDetailOne))
        val detailTwo: TextView = itemView.findViewById((R.id.tvDetailTwo))
        val detailThree: TextView = itemView.findViewById((R.id.tvDetailThree))
        val detailFour: TextView = itemView.findViewById((R.id.tvDeatailFour))
        val connectionCount: TextView = itemView.findViewById(R.id.tvConnectionCount)
        val count: TextView = itemView.findViewById(R.id.tvCount)
    }
    fun setData(newList:List<FaithconxUser>){
        myList= newList as ArrayList<FaithconxUser>
        notifyDataSetChanged()
    }
}