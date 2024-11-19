package com.example.bottomnavigation.person

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.bottomnavigation.LoginActivity
import com.example.bottomnavigation.R
import com.google.firebase.auth.FirebaseAuth


class PersonFragment : Fragment() {
    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?
    ):View? {
        val view = inflater.inflate(R.layout.fragment_person, container, false)
        auth=FirebaseAuth.getInstance()
        val currentUser=auth.currentUser
        val imgEmail=view.findViewById<ImageView>(R.id.imgEmail)
        val textName=view.findViewById<TextView>(R.id.textName)
        val textEmail=view.findViewById<TextView>(R.id.textEmail)
        textName.text=currentUser?.displayName
        textEmail.text=currentUser?.email
        Glide.with(this).load(currentUser?.photoUrl).circleCrop().into(imgEmail)
        val btnSignOut=view.findViewById<Button>(R.id.btn_SignOut)
        btnSignOut.setOnClickListener {
            val builder= AlertDialog.Builder(activity)
            builder.setTitle("Sign Out")
            builder.setMessage("Are you sure you want to sign out?")
            builder.setPositiveButton("Yes") { dialog, which ->

            auth.signOut()
            val signIn= Intent(activity, LoginActivity::class.java)
            signIn.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(signIn)}
            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            val dialog=builder.create()
            dialog.show()
        }
        return view
    }

}