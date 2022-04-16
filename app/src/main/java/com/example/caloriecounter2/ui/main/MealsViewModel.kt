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

    private var _selectedDate : MutableLiveData<Date> = MutableLiveData<Date>(Calendar.getInstance().time)
    val selectedDate: LiveData<Date> get() = _selectedDate;

    init {
        _mealsRepository = mealsRepository
        viewModelScope.launch {
            mealList = _mealsRepository.getAllMealsForDate(selectedDate.value);
        }
    }

    fun nextDay() {
        _selectedDate.value = Date(2022,4, 3);
    }

    fun previousDay() {
        _selectedDate.value = Date(2022,4,2)
    }

    suspend fun delete(meal: Meal) {
        _mealsRepository.delete(meal);
    }

}