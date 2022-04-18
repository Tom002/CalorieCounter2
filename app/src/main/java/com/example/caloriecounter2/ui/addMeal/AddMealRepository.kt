package com.example.caloriecounter2.ui.addMeal

import com.example.caloriecounter2.model.db.Meal
import com.example.caloriecounter2.model.network.AddMealDto
import com.example.caloriecounter2.network.MealService
import com.example.caloriecounter2.persistence.MealDao
import javax.inject.Inject

class AddMealRepository @Inject constructor (
    private val mealService: MealService,
    private val mealDao: MealDao
) {
    suspend fun add(meal: Meal) {
        val addDto = AddMealDto(meal.name, meal.calories, meal.proteinInGrams, meal.carbInGrams, meal.fatInGrams, meal.date)
        val response = mealService.addMeal(addDto).execute()

        // HA sikeres
        if(response.isSuccessful)
            mealDao.insertMeal(meal)

        throw Exception();
    }
}