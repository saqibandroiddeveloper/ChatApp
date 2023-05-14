package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chatapp.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject
@AndroidEntryPoint
class Login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
  @Inject
  lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }
        binding.btnSignin.setOnClickListener {
            val email = binding.etEmail.text
            val password = binding.etPassword.text
            login(email.toString(),password.toString())
        }

    }

    private fun login(email: String, password: String) {
      mAuth.signInWithEmailAndPassword(email,password)
          .addOnFailureListener { p0 -> Log.d("TAG", "onFailure: ${p0.message}") }
          .addOnCompleteListener(this){task->
          if (task.isSuccessful){
              val intent = Intent(this,MainActivity::class.java)
              finish()
              startActivity(intent)
          }else{
              Toast.makeText(this, "Login Failed! Try Again", Toast.LENGTH_SHORT).show()
          }
      }
    }
}