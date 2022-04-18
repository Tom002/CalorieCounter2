package com.example.caloriecounter2.ui.editMeal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.caloriecounter2.model.db.Meal
import com.example.caloriecounter2.model.network.MealDto
import com.example.caloriecounter2.network.MealService
import com.example.caloriecounter2.persistence.MealDao
import javax.inject.Inject

class EditMealRepository @Inject constructor (
    private val mealService: MealService,
    private val mealDao: MealDao
) {
    private var _mealMutableLiveData: MutableLiveData<Meal> = MutableLiveData()
    val meal: LiveData<Meal> get() = _mealMutableLiveData

    suspend fun update(mealToUpdate: Meal) {
        val updateDto = MealDto(mealToUpdate.id, mealToUpdate.name, mealToUpdate.calories, mealToUpdate.proteinInGrams, mealToUpdate.carbInGrams, mealToUpdate.fatInGrams, mealToUpdate.date)
        val response = mealService.updateMeal(updateDto).execute()

        // HA sikeres
        if(response.isSuccessful)
            mealDao.updateMeal(mealToUpdate)

        throw Exception();
    }

     suspend fun getMealById(mealId: Long) : Meal {
         var meal = mealDao.getMeal(mealId);
         if(meal != null)
             return meal

         throw Exception()
    }
}