package com.example.bottomnavigation


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    lateinit var Auth:FirebaseAuth
    lateinit var googleSignInClient:GoogleSignInClient
    lateinit var imgLogin:ImageView
    lateinit var btnGuest:Button
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        imgLogin=findViewById(R.id.imgLogin)
        btnGuest=findViewById(R.id.btn_guest)
        val sharedPreferences=getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        editor.putBoolean("isLoggedIn",false)
        editor.apply()
        btnGuest.setOnClickListener {
            val builder= AlertDialog.Builder(this)
            builder.setTitle("Guest Login")
            builder.setMessage("Are you sure you want to login as a guest?")
            builder.setPositiveButton("Yes") { _, _ ->

            val dashboard= Intent(this, MainActivity::class.java)
            startActivity(dashboard)
            editor.putBoolean("isLoggedIn",true)
            editor.apply()

            finish()}
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog=builder.create()
            dialog.show()

        }

        Glide.with(this).load("https://images.pexels.com/photos/11538098/pexels-photo-11538098.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1").into(imgLogin)

        val gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient= GoogleSignIn.getClient(this,gso)
        Auth= FirebaseAuth.getInstance()
        val signInButton=findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setOnClickListener {
            editor.putBoolean("isLoggedIn",false)
            editor.apply()
            signIn()
        }
    }
    private fun signIn()
    {
        val signInIntent=googleSignInClient.signInIntent
        startActivityForResult(signInIntent,100)
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception=task.exception
            if(task.isSuccessful){
                try {
                    val account=task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity","firebaseAuthWithGoogle"+account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                }
                catch (e:ApiException)
                {
                    Log.w("SignInActivity","Google sign in failed",e)
                }

            }
            else{
                Log.w("SignInActivity",exception.toString())
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken:String)
    {
        val credential= GoogleAuthProvider.getCredential(idToken,null)
        Auth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful)
                {
                    Log.d("SignInActivity","signInWithCredential:success")
                    val dashboard= Intent(this, MainActivity::class.java)
                    startActivity(dashboard)
                    finish()
                }
                else
                {
                    Log.d("SignInActivity","signInWithCredential:failure")
                }
            }

    }
}