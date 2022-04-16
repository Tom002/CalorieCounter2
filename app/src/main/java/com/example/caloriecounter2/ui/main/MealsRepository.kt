package com.example.caloriecounter2.ui.main

import androidx.lifecycle.LiveData
import com.example.caloriecounter2.model.db.Meal
import com.example.caloriecounter2.model.network.AddMealDto
import com.example.caloriecounter2.network.MealService
import com.example.caloriecounter2.persistence.MealDao
import java.util.*
import javax.inject.Inject

class MealsRepository @Inject constructor (
    private val mealService: MealService,
    private val mealDao: MealDao)
{
    suspend fun getAllMealsForDate(date: Date?) : LiveData<List<Meal>> {
        return mealDao.getMealListForDate(date);
    }

    suspend fun insert(meal: Meal) {
        val addMeal = AddMealDto(meal.name, meal.calories, meal.proteinInGrams, meal.carbInGrams, meal.fatInGrams, meal.date)
        val response = mealService.addMeal(addMeal).execute()

        if(response.isSuccessful)
            mealDao.insertMeal(meal)
    }

    suspend fun delete(meal: Meal) {
        val response = mealService.deleteMeal(meal.id).execute()

        if(response.isSuccessful)
            mealDao.deleteMeal(meal)
    }
}