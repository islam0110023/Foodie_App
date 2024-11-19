package com.example.bottomnavigation.modelCountry


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("meals") val meals: List<CountryX>?
)