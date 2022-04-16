package com.example.caloriecounter2.ui.editMeal

import com.example.caloriecounter2.model.db.Meal
import com.example.caloriecounter2.model.network.MealDto
import com.example.caloriecounter2.network.MealService
import com.example.caloriecounter2.persistence.MealDao
import javax.inject.Inject

class EditMealRepository @Inject constructor (
    private val mealService: MealService,
    private val mealDao: MealDao
) {

    suspend fun update(meal: Meal) {
        val updateDto = MealDto(meal.id, meal.name, meal.calories, meal.proteinInGrams, meal.carbInGrams, meal.fatInGrams, meal.date)
        val response = mealService.updateMeal(updateDto).execute()

        // HA sikeres
        if(response.isSuccessful)
            mealDao.updateMeal(meal)

        throw Exception();
    }
}