package com.example.chatapp.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapp.Message
import com.example.chatapp.R
import com.google.firebase.auth.FirebaseAuth

class MessageAdaptor(val context:Context, val messageList: ArrayList<Message> ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
   private val ITEM_SENT = 2
    private val ITEM_RECEIVE = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType==1){
            val view = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            ReceiveViewHolder(view)
        }else{
            val view = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if(holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
        }else{
          val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            ITEM_SENT
        }else{
            ITEM_RECEIVE
        }

    }
    override fun getItemCount(): Int {
      return messageList.size
    }

    class SentViewHolder(itemView: View):ViewHolder(itemView){
  val sentMessage = itemView.findViewById<TextView>(R.id.sendMessage)
    }
    class ReceiveViewHolder(itemView: View):ViewHolder(itemView){
  val receiveMessage = itemView.findViewById<TextView>(R.id.receiveMessage)
    }
}