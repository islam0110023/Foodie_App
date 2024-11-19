package com.example.bottomnavigation.modelCategory

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("categories") val categories: List<CategoryX>?
)
