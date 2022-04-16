package com.example.caloriecounter2.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.caloriecounter2.model.db.Meal
import java.util.*

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Query("SELECT * FROM MEAL WHERE id = :id_")
    suspend fun getMeal(id_: Long): Meal?

    @Query("SELECT * FROM Meal WHERE date = :date_")
    fun getMealListForDate(date_: Date?): LiveData<List<Meal>>

    @Update()
    suspend fun updateMeal(meal: Meal)

    @Delete()
    suspend fun deleteMeal(meal: Meal)
}