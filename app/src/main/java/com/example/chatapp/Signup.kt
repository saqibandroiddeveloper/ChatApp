package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    @Inject
    lateinit var mAuth: FirebaseAuth
    @Inject
    lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.SignupBtn.setOnClickListener {
            val username = binding.etName.text
            val email = binding.etEmail.text
            val password = binding.etPassword.text
            signUp(username.toString(),email.toString(),password.toString())
        }
    }

    private fun signUp(username:String,email: String, password: String) {
     mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){ task->
         if (task.isSuccessful){
           //Now Add to Realtime Database
            val uid = mAuth.currentUser?.uid!!
             database.child("user").child(uid).setValue(User(username,email,uid))
             val intent = Intent(this,MainActivity::class.java)
             finish()
             startActivity(intent)
         }else{
             Toast.makeText(this, "SignUp Failed! Try Again", Toast.LENGTH_LONG).show()
         }
     }
    }
}