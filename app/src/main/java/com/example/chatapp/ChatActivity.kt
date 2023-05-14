package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.adaptor.MessageAdaptor
import com.example.chatapp.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    @Inject
    lateinit var mAuth:FirebaseAuth
    @Inject
    lateinit var database:DatabaseReference
    lateinit var chatRecyclerView: RecyclerView
    lateinit var messageAdaptor: MessageAdaptor
    lateinit var messageList: ArrayList<Message>
    var receiverRoom:String?=null
    var sendRoom:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatRecyclerView = binding.chatRecycler
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        messageList = ArrayList()
        messageAdaptor = MessageAdaptor(this,messageList)
        chatRecyclerView.adapter = messageAdaptor


        val name = intent.getStringExtra("name")
        supportActionBar?.title = name

        val receiverUid = intent.getStringExtra("uid")
        val senderId = mAuth.uid
//Adding data to recyclerview
        database.child("chats").child(sendRoom!!).child("messages").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
           messageList.clear()
                for (postSnapShot in snapshot.children){
                    val message = postSnapShot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                messageAdaptor.notifyDataSetChanged()

            }
            override fun onCancelled(error: DatabaseError) {

            }

        })


        binding.sendMessageBtn.setOnClickListener {
             val message = binding.etMessage.text
            val messageObj = Message(message.toString(),senderId)
            database.child("chats").child(sendRoom!!).child("messages").push()
                .setValue(messageObj).addOnSuccessListener {
                    database.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObj)
                }
            message.clear()
        }

    }
}