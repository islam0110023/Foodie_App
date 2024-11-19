package com.example.bottomnavigation.showMeal

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavigation.modelIngredient.IngredientX
import com.example.bottomnavigation.R
import com.example.bottomnavigation.showCountryMeal.CountryMealActivity

class MyAdapterIngredient(var ingredients: List<IngredientX>): RecyclerView.Adapter<MyAdapterIngredient.MyViewHolder>() {
    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val txt_ingr = view.findViewById<TextView>(R.id.txt_ingr)
        val img_ingr = view.findViewById<ImageView>(R.id.img_ingr)
        val clicked = view.findViewById<ConstraintLayout>(R.id.clicked)
    }
    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return MyViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder:MyViewHolder, position:Int) {
        val currentIngredient = ingredients[position]
        holder.txt_ingr.text = currentIngredient.strIngredient
        Glide.with(holder.view.context)
            .load("https://www.themealdb.com/images/ingredients/${currentIngredient.strIngredient}.png")
            .into(holder.img_ingr)
        holder.clicked.setOnClickListener {
            val intent = Intent(holder.view.context, CountryMealActivity::class.java)
            intent.putExtra("ingredient", currentIngredient.strIngredient)
            holder.view.context.startActivity(intent)
        }
    }
    //https://www.themealdb.com/images/category/beef.png

    override fun getItemCount():Int {
       return ingredients.size
    }



}