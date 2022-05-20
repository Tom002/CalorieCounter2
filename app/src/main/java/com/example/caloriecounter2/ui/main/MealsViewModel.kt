package com.example.caloriecounter2.ui.main

import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.caloriecounter2.model.db.Meal
import com.example.caloriecounter2.persistence.AppDatabase
import com.example.caloriecounter2.ui.addMeal.AddMealActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import retrofit2.HttpException
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    mealsRepository: MealsRepository,
    application: Application
) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val _mealsRepository: MealsRepository
    val mealList: LiveData<List<Meal>> get() = _mealsRepository.mealList

    val currentDate: LiveData<DateTime>
        get() = _mealsRepository.selectedDate

    val totalCalories : LiveData<Int>
        get() = _mealsRepository.totalCalories

    val totalProtein : LiveData<Double>
        get() = _mealsRepository.totalProtein

    val totalCarbs : LiveData<Double>
        get() = _mealsRepository.totalCarbs

    val totalFat : LiveData<Double>
        get() = _mealsRepository.totalFat

    init {
        _mealsRepository = mealsRepository
        viewModelScope.launch {
            _mealsRepository.getAllMealsForDate();
        }
    }

    fun nextDay() {
        viewModelScope.launch {
            _mealsRepository.nextDay()
            _mealsRepository.getAllMealsForDate();
        }
    }

    fun previousDay() {
        viewModelScope.launch {
            _mealsRepository.prevDay()
            _mealsRepository.getAllMealsForDate();
        }
    }

    fun delete(meal: Meal) {
        viewModelScope.launch {
            try {
                _mealsRepository.delete(meal);
                val toast = Toast.makeText(context, "Meal deleted successfully", Toast.LENGTH_LONG)
                toast.show()
            }
            catch (e: HttpException)
            {
                val toast = Toast.makeText(context, "Unexpected error while deleting meal", Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

    fun addMeal() {
        val intent = Intent(context, AddMealActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent)
    }
}