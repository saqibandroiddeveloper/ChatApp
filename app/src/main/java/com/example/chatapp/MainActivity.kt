package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.adaptor.UserAdaptor
import com.example.chatapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var listAdapter: UserAdaptor
    lateinit var recyclerView: RecyclerView
    lateinit var userList:ArrayList<User>
    @Inject
    lateinit var mAuth: FirebaseAuth
    @Inject
    lateinit var database: DatabaseReference
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /********************************Initialize Here********************************************/
        recyclerView = binding.userRecyclerView
        listAdapter = UserAdaptor(this)
        userList = arrayListOf()
        /*********************************End Here**************************************************/
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listAdapter

        database.child("user").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (user in snapshot.children){
                    val currentUser = user.getValue(User::class.java)!!
                    if (mAuth.currentUser?.uid != currentUser.uid) userList.add(currentUser)
                }
                listAdapter.submitList(userList)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.logout){
          mAuth.signOut()
            finish()
        }
        return true

    }
}