package com.example.testui.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testui.databinding.ListDetailsBinding
import com.example.testui.model.ResultsItem
import com.example.testui.model.UserDetails


class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){


    private var result= mutableListOf<ResultsItem>()

    fun setUserData( result:List<ResultsItem>){
        this.result=result.toMutableList()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding=ListDetailsBinding.inflate(inflater,parent,false)
        return UserViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val result = result[position]
        holder.binding.title.text=result.name?.first.toString()
        holder.binding.tvDetailOne.text=result.location?.city.toString()
        holder.binding.tvDetailTwo.text=result.location?.state.toString()
        holder.binding.tvDetailThree.text=result.location?.country.toString()
        holder.binding.tvDetailFour.text=result.location?.postcode.toString()
        holder.binding.tvConnectionCount.text=result.dob?.age.toString()
        holder.binding.tvCount.text=result.dob?.date.toString()
    }
    class UserViewHolder( val binding:ListDetailsBinding) : RecyclerView.ViewHolder(binding.root)
}


