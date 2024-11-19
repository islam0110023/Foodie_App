package com.example.bottomnavigation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class StartProjActivity : AppCompatActivity() {
    private lateinit var Auth:FirebaseAuth
    private lateinit var imgStart:ImageView
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_proj)
        imgStart=findViewById(R.id.imgStart)
       // Glide.with(this).load("https://images.pexels.com/photos/1667427/pexels-photo-1667427.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1").into(imgStart)
        Auth=FirebaseAuth.getInstance()
        val user=Auth.currentUser
        Handler().postDelayed({
            if(user!=null){
                val dashboard= Intent(this,MainActivity::class.java)
                startActivity(dashboard)
                finish()
            }else{
                val signIn= Intent(this, LoginActivity::class.java)
                startActivity(signIn)
                finish()

            }
        },0)

    }
}