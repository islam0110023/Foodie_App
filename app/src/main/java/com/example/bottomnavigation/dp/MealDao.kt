package com.example.bottomnavigation.dp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bottomnavigation.modelMeal.MealX


@Dao
interface MealDao {
    @Query("SELECT * FROM meal")
    fun getAllMeals(): LiveData<List<MealX>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMeal(vararg meals: MealX): List<Long>

    @Delete
    suspend fun delete(meal: MealX): Int

}
