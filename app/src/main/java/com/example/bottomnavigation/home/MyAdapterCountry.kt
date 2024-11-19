package com.example.bottomnavigation.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.R
import com.example.bottomnavigation.modelCountry.CountryX
import com.example.bottomnavigation.showCountryMeal.CountryMealActivity

class MyAdapterCountry(var countries: List<CountryX>): RecyclerView.Adapter<MyAdapterCountry.MyViewHolder>() {
    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val btn_country = view.findViewById<Button>(R.id.btn_country)
    }

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.country_row, parent, false)
        return MyViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder:MyViewHolder, position:Int) {
        val currentCountry = countries[position]
        holder.btn_country.text = currentCountry.strArea
       holder.btn_country.setOnClickListener {
           val intent = Intent(holder.view.context, CountryMealActivity::class.java)
           intent.putExtra("country", currentCountry.strArea)
           holder.view.context.startActivity(intent)

        }

    }

    override fun getItemCount():Int {
        return countries.size
    }
}