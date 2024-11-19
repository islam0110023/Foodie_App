package com.example.bottomnavigation.modelIngredient


import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("ingredients")
    val ingredients: List<IngredientX?>? = null
)