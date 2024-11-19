package com.example.bottomnavigation.network

import com.example.bottomnavigation.modelMeal.Meal
import com.example.bottomnavigation.modelCategory.Category
import com.example.bottomnavigation.modelCountry.Country
import com.example.bottomnavigation.modelIngredient.Ingredient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MealService {
      @GET("search.php?s=")
      suspend fun getMealByName(@Query("s") name: String): Response<Meal>
      @GET("random.php")
      suspend fun getRandomMeal(): Response<Meal>
//      @GET("list.php?i=list")
//      suspend fun getIngredientList(): Response<Ingredient>
      @GET("list.php?a=list")
      suspend fun getCountry(): Response<Country>
      @GET("filter.php?a=")
      suspend fun getMealByCountry(@Query("a") country: String): Response<Meal>
      @GET("categories.php")
      suspend fun getCategory(): Response<Category>
      @GET("filter.php?c=")
      suspend fun getMealByCategory(@Query("c") category: String): Response<Meal>
      @GET("filter.php?i=")
      suspend fun getMealByIngredient(@Query("i") ingredient: String): Response<Meal>



}