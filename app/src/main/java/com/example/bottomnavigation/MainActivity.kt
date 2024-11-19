package com.example.bottomnavigation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bottomnavigation.favorite.FavoriteFragment
import com.example.bottomnavigation.home.HomeFragment
import com.example.bottomnavigation.person.PersonFragment
import com.example.bottomnavigation.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView


    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val favoriteFragment = FavoriteFragment()
    private val personFragment = PersonFragment()

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val navHostFragment=supportFragmentManager.findFragmentById(R.id.nav_host_fragment)as NavHostFragment
//        val navController=navHostFragment.navController
//
          bottomNavigationView=findViewById(R.id.bottom_navigation_view)
//        bottomNavigationView.setupWithNavController(navController)
        val isGuest=checkIsGuest()

        setCurrentFragment(homeFragment)


        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.personFragment, R.id.favoriteFragment -> {
                    if (isGuest) {
                        showLoginDialog()
                        false
                    } else {
                        setCurrentFragment(when (menuItem.itemId) {
                            R.id.favoriteFragment -> favoriteFragment
                            R.id.personFragment -> personFragment
                            else -> homeFragment
                        })
                        true
                    }
                }
                else -> {
                    setCurrentFragment(when (menuItem.itemId) {
                        R.id.homeFragment -> homeFragment
                        R.id.searchFragment -> searchFragment
                        else -> homeFragment
                    })
                    true
                }
            }
        }

    }


    private fun checkIsGuest():Boolean
    {
        val sharedPreferences=getSharedPreferences("MyPrefs", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn",false)
    }
    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, fragment)
            addToBackStack(null)
            commit()
        }
    }
    private fun showLoginDialog(){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Sign Up More Features")
        builder.setMessage("You must be logged in to add meals to favorites.")
        builder.setPositiveButton("Sign Up") { dialog, which ->
            val intent= Intent(this,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }
        val dialog=builder.create()
        dialog.show()
    }


}