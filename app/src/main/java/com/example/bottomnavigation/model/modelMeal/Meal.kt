package com.example.bottomnavigation.modelMeal


import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("meals")
    val meals: List<MealX>? = listOf()
)