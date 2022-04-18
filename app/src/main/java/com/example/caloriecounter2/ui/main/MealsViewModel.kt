package com.example.caloriecounter2.ui.main

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.caloriecounter2.model.db.Meal
import com.example.caloriecounter2.persistence.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    mealsRepository: MealsRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _mealsRepository: MealsRepository
    lateinit var mealList: LiveData<List<Meal>>

    val currentDate: LiveData<DateTime>
        get() = _mealsRepository.selectedDate

    init {
        _mealsRepository = mealsRepository
        viewModelScope.launch {
            mealList = _mealsRepository.getAllMealsForDate();
        }
    }

    fun nextDay() {
        viewModelScope.launch {
            _mealsRepository.nextDay()
            mealList = _mealsRepository.getAllMealsForDate();
        }
    }

    fun previousDay() {
        viewModelScope.launch {
            _mealsRepository.prevDay()
            mealList = _mealsRepository.getAllMealsForDate();
        }
    }

    fun delete(meal: Meal) {
        viewModelScope.launch {
            _mealsRepository.delete(meal);
        }
    }
}