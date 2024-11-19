package com.example.bottomnavigation.showCountryMeal

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.R
import com.example.bottomnavigation.dp.MealDatabase
import com.example.bottomnavigation.home.Factory
import com.example.bottomnavigation.home.HomeMealViewModel
import com.example.bottomnavigation.home.MyAdapterMealR
import com.example.bottomnavigation.modelMeal.MealX
import com.example.bottomnavigation.network.RetrofitHealper
import com.example.bottomnavigation.utils.MyMealListener

class CountryMealActivity : AppCompatActivity(), MyMealListener {
    lateinit var viewModel: HomeMealViewModel
    lateinit var adapter2: MyAdapterMealR
    lateinit var rv_country: RecyclerView
    lateinit var btnExit:Button
    lateinit var txt_country:TextView
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_meal)
        initUi()
        setUpViewModel()
        btnExit.setOnClickListener{
            finish()
        }
        val country = intent.getStringExtra("country")
        val category = intent.getStringExtra("category")
        val ingredient = intent.getStringExtra("ingredient")

        if(category!=null){

        category?.let {
            viewModel.getMealByCategory(it)
            txt_country.text=category+" Meals"
        }

        }
        else if(ingredient!=null){
            ingredient?.let {
                viewModel.getMealByIngredient(it)
                txt_country.text=ingredient+" Meals"
            }
        }
        else{
        country?.let {
            viewModel.getMealByCountry(it)
            txt_country.text=country+" Meals"
        } ?: run {
            Toast.makeText(this, "Meal name is missing.", Toast.LENGTH_SHORT).show()

        }
        }
        viewModel.MealByCountry.observe(this) {
            adapter2.meals = it
            adapter2.notifyDataSetChanged()
        }
        viewModel.message.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }


    }
    fun initUi() {
        btnExit = findViewById(R.id.btnExit)
        txt_country = findViewById(R.id.txt_countryMeal)
        rv_country = findViewById(R.id.rv_countryMeal)
        adapter2 = MyAdapterMealR(emptyList(),this)
        rv_country.adapter = adapter2
        rv_country.layoutManager= GridLayoutManager(this,2)
    //LinearLayoutManager(this)
    }

    override fun onMealClicked(meal:MealX) {
        viewModel.addMeal(meal)
    }
    fun setUpViewModel() {
        val retrofit = RetrofitHealper.service
        val dao = MealDatabase.getInstance(this).mealDao()
        viewModel = ViewModelProvider(this, Factory(dao, retrofit)).get(HomeMealViewModel::class.java)
    }
}