package com.example.bottomnavigation.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavigation.modelCategory.CategoryX
import com.example.bottomnavigation.R
import com.example.bottomnavigation.showCountryMeal.CountryMealActivity

class MyAdapterCategory(var categories: List<CategoryX>): RecyclerView.Adapter<MyAdapterCategory.MyViewHolder>() {
    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val img_category = view.findViewById<ImageView>(R.id.imgCategory)
        val txt_category = view.findViewById<TextView>(R.id.txtCategory)
        val clickCategory = view.findViewById<ConstraintLayout>(R.id.clickCategory)
    }
    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.category_row, parent, false)
        return MyViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder:MyViewHolder, position:Int) {
        val currentCategory = categories[position]
        holder.txt_category.text = currentCategory.strCategory
        Glide.with(holder.view.context).load(currentCategory.strCategoryThumb).into(holder.img_category)
        holder.clickCategory.setOnClickListener {
            val intent = Intent(holder.view.context, CountryMealActivity::class.java)
            intent.putExtra("category", currentCategory.strCategory)
            holder.view.context.startActivity(intent)
        }

    }

    override fun getItemCount():Int {
       return categories.size
    }


}