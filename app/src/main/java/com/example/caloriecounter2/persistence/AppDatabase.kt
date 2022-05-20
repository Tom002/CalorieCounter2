package com.example.caloriecounter2.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.caloriecounter2.model.db.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao
}