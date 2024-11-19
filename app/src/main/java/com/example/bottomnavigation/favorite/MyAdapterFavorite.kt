package com.example.bottomnavigation.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavigation.modelMeal.MealX
import com.example.bottomnavigation.utils.MyMealListener
import com.example.bottomnavigation.R
import com.example.bottomnavigation.showMeal.ShowMealActivity

class MyAdapterFavorite(var meals: List<MealX>, val myMealListener: MyMealListener): RecyclerView.Adapter<MyAdapterFavorite.MyViewHolder>() {
    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val txtMeal = view.findViewById<TextView>(R.id.txtMeal)
        val imgMeal = view.findViewById<ImageView>(R.id.imgMeal)
        val btnAdd = view.findViewById<Button>(R.id.btnAddF)
        val click =view.findViewById<ConstraintLayout>(R.id.click)


    }
    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):MyViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.meal_row2, parent, false)
        return MyViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder:MyViewHolder, position:Int) {
        val meal = meals[position]
        holder.txtMeal.text = meal.strMeal
        Glide.with(holder.view.context).load(meal.strMealThumb).into(holder.imgMeal)
        holder.btnAdd.text = "Remove Favorite"
        holder.btnAdd.setOnClickListener {
            myMealListener.onMealClicked(meal)

        }
        holder.click.setOnClickListener {
           val intent = Intent(holder.view.context, ShowMealActivity::class.java)
            intent.putExtra("meal", meal.strMeal)
            holder.view.context.startActivity(intent)

        }
    }

    override fun getItemCount():Int {
        return meals.size
    }


}