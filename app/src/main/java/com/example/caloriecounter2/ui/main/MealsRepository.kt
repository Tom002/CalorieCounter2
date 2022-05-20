package com.example.caloriecounter2.ui.main

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.caloriecounter2.model.db.Meal
import com.example.caloriecounter2.model.network.AddOrEditMealDto
import com.example.caloriecounter2.network.MealService
import com.example.caloriecounter2.persistence.MealDao
import org.joda.time.DateTime
import retrofit2.HttpException
import javax.inject.Inject


class MealsRepository @Inject constructor (
    private val mealService: MealService,
    private val mealDao: MealDao)
{
    private var _mealList : MutableLiveData<List<Meal>> = MutableLiveData<List<Meal>>()
    val mealList: LiveData<List<Meal>> get() = _mealList

    private var _selectedDate : MutableLiveData<DateTime> = MutableLiveData<DateTime>(DateTime())
    val selectedDate: LiveData<DateTime> get() = _selectedDate

    private var _totalCalories : MutableLiveData<Int> = MutableLiveData<Int>(0)
    val totalCalories: LiveData<Int> get() = _totalCalories

    private var _totalProtein : MutableLiveData<Double> = MutableLiveData<Double>(0.0)
    val totalProtein: LiveData<Double> get() = _totalProtein

    private var _totalFat : MutableLiveData<Double> = MutableLiveData<Double>(0.0)
    val totalFat: LiveData<Double> get() = _totalFat

    private var _totalCarbs: MutableLiveData<Double> = MutableLiveData<Double>(0.0)
    val totalCarbs: LiveData<Double> get() = _totalCarbs

    fun nextDay() {
        val nextDay = selectedDate.value?.plusDays(1) ?: DateTime().plusDays(1)
        _selectedDate.value = nextDay
    }

    fun prevDay() {
        val prevDay = selectedDate.value?.minusDays(1) ?: DateTime().minusDays(1)
        _selectedDate.value = prevDay
    }

    suspend fun getAllMealsForDate() {
        var mealsInDb : List<Meal> = mealDao.getMealListForDate(
            selectedDate.value!!.year, selectedDate.value!!.monthOfYear, selectedDate.value!!.dayOfMonth)
        if(mealsInDb.isEmpty())
        {
            // Első lefutás, lekérdezzük API-ból az elemeket
            val mealsFromApi = mealService.fetchMealsForDate(selectedDate.value?.year, selectedDate.value?.monthOfYear, selectedDate.value?.dayOfMonth)
            var result : List<Meal> = emptyList()

            if(!mealsFromApi.isEmpty()) {
                mealsFromApi.forEach {
                    var dateTime = DateTime(it.date)
                    val meal = Meal(it.id, it.name, it.calories, it.proteinInGrams, it.carbInGrams, it.fatInGrams, dateTime.year, dateTime.monthOfYear, dateTime.dayOfMonth)
                    mealDao.insertMeal(meal)
                    result = result + meal
                }
            }
            _mealList.value  = result
        }
         else{
            _mealList.value = mealsInDb
        }
        refreshSummaryTable()
    }

    suspend fun delete(meal: Meal) {
        mealService.deleteMeal(meal.id)
        mealDao.deleteMeal(meal)

        _mealList.value = mealDao.getMealListForDate(
            selectedDate.value!!.year, selectedDate.value!!.monthOfYear, selectedDate.value!!.dayOfMonth)
        refreshSummaryTable()
    }

    suspend fun update(mealToUpdate: Meal) {
        val updateDto = AddOrEditMealDto(
            mealToUpdate.name,
            mealToUpdate.calories,
            mealToUpdate.proteinInGrams,
            mealToUpdate.carbInGrams,
            mealToUpdate.fatInGrams,
            DateTime(mealToUpdate.year,
                mealToUpdate.month,
                mealToUpdate.day,0,0).toDate()
        )
        mealService.updateMeal(mealToUpdate.id, updateDto)
        mealDao.updateMeal(mealToUpdate)

        _mealList.value = mealDao.getMealListForDate(
            selectedDate.value!!.year, selectedDate.value!!.monthOfYear, selectedDate.value!!.dayOfMonth)
        refreshSummaryTable()
    }

    suspend fun getMealById(mealId: Long) : Meal {
        var mealFromDb = mealDao.getMeal(mealId);
        if(mealFromDb != null)
            return mealFromDb

        var mealFromApi = mealService.getMealById(mealId)
        val date = DateTime(mealFromApi.date)
        val mealToInsert = Meal(
            mealFromApi.id,
            mealFromApi.name,
            mealFromApi.calories,
            mealFromApi.proteinInGrams,
            mealFromApi.carbInGrams,
            mealFromApi.fatInGrams,
            date.year,
            date.monthOfYear,
            date.dayOfMonth
        )
        mealDao.insertMeal(mealToInsert)
        return mealToInsert
    }

    suspend fun add(meal: Meal) {
        val addDto = AddOrEditMealDto(meal.name, meal.calories, meal.proteinInGrams, meal.carbInGrams, meal.fatInGrams, DateTime(meal.year, meal.month, meal.day, 0, 0).toDate())
        var addeddMeal = mealService.addMeal(addDto)
        var mealToStore = Meal(addeddMeal.id, meal.name, meal.calories, meal.proteinInGrams, meal.carbInGrams, meal.fatInGrams, meal.year, meal.month, meal.day)
        mealDao.insertMeal(mealToStore)

        _mealList.value = mealDao.getMealListForDate(
            selectedDate.value!!.year, selectedDate.value!!.monthOfYear, selectedDate.value!!.dayOfMonth)
        refreshSummaryTable()
    }

    fun refreshSummaryTable() {
        val items = mealList.value!!
        var totalCalories = 0
        var totalProtein = 0.0
        var totalFat = 0.0
        var totalCarbs = 0.0
        if(!items.isEmpty()) {
            items.forEach {
                totalCalories += it.calories
                totalCarbs += it.carbInGrams
                totalFat += it.fatInGrams
                totalProtein += it.proteinInGrams
            }
        }
        _totalCalories.value = totalCalories
        _totalCarbs.value = totalCarbs
        _totalFat.value = totalFat
        _totalProtein.value = totalProtein
    }
}