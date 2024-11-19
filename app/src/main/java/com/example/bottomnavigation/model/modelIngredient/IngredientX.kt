package com.example.bottomnavigation.modelIngredient


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "ingredients")
data class IngredientX(
    @PrimaryKey()
    @SerializedName("idIngredient")
    val idIngredient: String? = null,
    @SerializedName("strDescription")
    val strDescription: String? = null,
    @SerializedName("strIngredient")
    val strIngredient: String? = null,
    @SerializedName("strType")
    val strType: String? = null
)