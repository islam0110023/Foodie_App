package com.example.bottomnavigation.dp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bottomnavigation.modelMeal.MealX

@Database(entities = [MealX::class], version = 1)
abstract class MealDatabase: RoomDatabase()  {
    abstract fun mealDao(): MealDao
    companion object {
        @Volatile
        private var INSTANCE: MealDatabase? = null
        fun getInstance(context:Context):MealDatabase{
            return INSTANCE?:synchronized(this){
                val tempInstance= Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "product_database"
                )
                    .build()
                INSTANCE=tempInstance
                tempInstance
            }
        }

    }

}

