package com.example.bottomnavigation.modelCategory

import com.google.gson.annotations.SerializedName

data class CategoryX(
    @SerializedName("idCategory") val idCategory: String?,
    @SerializedName("strCategory") val strCategory: String?,
    @SerializedName("strCategoryThumb") val strCategoryThumb: String?,
    @SerializedName("strCategoryDescription") val strCategoryDescription: String?
)
