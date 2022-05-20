package com.example.caloriecounter2.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.caloriecounter2.model.db.Meal
import java.util.*

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal) : Long

    @Query("SELECT * FROM MEAL WHERE id = :id_")
    suspend fun getMeal(id_: Long): Meal?

    @Query("SELECT * FROM Meal WHERE year = :year_ AND month = :month_ AND day = :day_")
    suspend fun getMealListForDate(year_: Int, month_: Int, day_: Int): List<Meal>

    @Update()
    suspend fun updateMeal(meal: Meal)

    @Delete()
    suspend fun deleteMeal(meal: Meal)
}