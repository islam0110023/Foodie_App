package com.example.bottomnavigation.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavigation.LoginActivity
import com.example.bottomnavigation.modelMeal.MealX
import com.example.bottomnavigation.utils.MyMealListener
import com.example.bottomnavigation.R
import com.example.bottomnavigation.showMeal.ShowMealActivity

class MyAdapterMealR(var meals: List<MealX>, val myMealListener: MyMealListener): RecyclerView.Adapter<MyAdapterMealR.ViewHolder>() {
    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val txtMeal = view.findViewById<TextView>(R.id.txtMeal)
        val imgMeal = view.findViewById<ImageView>(R.id.imgMeal)
        val btnAdd = view.findViewById<Button>(R.id.btnAddF)
        val click =view.findViewById<ConstraintLayout>(R.id.click)
        val prefs = view.context.getSharedPreferences("MyPrefs", AppCompatActivity.MODE_PRIVATE)
        val isGuest = prefs.getBoolean("isLoggedIn", false)


    }
    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolder {
        val layoutInflater1 = LayoutInflater.from(parent.context).inflate(R.layout.meal_row, parent, false)
       val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.meal_row2, parent, false)

        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder:ViewHolder, position:Int) {
        val meal = meals[position]
        holder.txtMeal.text = meal.strMeal
        Glide.with(holder.view.context).load(meal.strMealThumb).into(holder.imgMeal)
        holder.btnAdd.setOnClickListener {
            if (holder.isGuest) {
                val builder= AlertDialog.Builder(holder.view.context)
                builder.setTitle("Sign Up More Features")
                builder.setMessage("You must be logged in to add meals to favorites.")
                builder.setPositiveButton("Sign Up") { dialog, which ->
                    val signInIntent = Intent(holder.view.context, LoginActivity::class.java)
                    signInIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    holder.view.context.startActivity(signInIntent)

                }
                builder.setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog=builder.create()
                dialog.show()

               // Toast.makeText(holder.view.context, "You must be logged in to add meals to favorites.", Toast.LENGTH_LONG).show()
            }
            else{
            myMealListener.onMealClicked(meal)
            }

        }
        holder.click.setOnClickListener {
            val intent = Intent(holder.view.context, ShowMealActivity::class.java)
            intent.putExtra("meal", meal.strMeal)
            holder.view.context.startActivity(intent)

        }
//        if (holder.isGuest) {
//            holder.btnAdd.visibility = View.GONE
//        } else {
//            holder.btnAdd.visibility = View.VISIBLE
//        }

    }

    override fun getItemCount():Int {
    return meals.size
    }



}