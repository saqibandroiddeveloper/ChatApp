package com.example.chatapp.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapp.ChatActivity
import com.example.chatapp.User
import com.example.chatapp.databinding.UserListBinding

class UserAdaptor(val context: Context):ListAdapter<User,UserAdaptor.MyViewHolder>(MyDiffUtills()) {

    inner class MyViewHolder(private val binding: UserListBinding):ViewHolder(binding.root){
        fun onBind(user: User){
            binding.username.text = user.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = UserListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("name",user.name)
            intent.putExtra("uid",user.uid)
            context.startActivity(intent)
        }
        holder.onBind(user)
    }
    class MyDiffUtills:DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
          return  oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.equals(newItem)
        }

    }
}