package com.example.caloriecounter2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.caloriecounter2.model.db.Meal
import com.example.caloriecounter2.model.network.AddMealDto
import com.example.caloriecounter2.network.MealService
import com.example.caloriecounter2.persistence.MealDao
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject


class MealsRepository @Inject constructor (
    private val mealService: MealService,
    private val mealDao: MealDao)
{

    private var _selectedDate : MutableLiveData<DateTime> = MutableLiveData<DateTime>(DateTime())
    val selectedDate: LiveData<DateTime> get() = _selectedDate

    fun nextDay() {
        val nextDay = selectedDate.value?.plusDays(1) ?: DateTime().plusDays(1)
        _selectedDate.value = nextDay
    }

    fun prevDay() {
        val prevDay = selectedDate.value?.minusDays(1) ?: DateTime().minusDays(1)
        _selectedDate.value = prevDay
    }

    fun getAllMealsForDate() : LiveData<List<Meal>> {
        return mealDao.getMealListForDate(selectedDate.value?.toDate());
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