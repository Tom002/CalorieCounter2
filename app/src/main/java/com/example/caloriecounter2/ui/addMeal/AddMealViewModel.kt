package com.example.caloriecounter2.ui.addMeal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.caloriecounter2.model.db.Meal
import com.example.caloriecounter2.ui.editMeal.EditMealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddMealViewModel @Inject constructor(
    addMealRepository: AddMealRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _addMealRepository: AddMealRepository

    val mealName = MutableLiveData<String>()
    val mealCalorieCount = MutableLiveData<Int>(0)
    val mealProteinCount = MutableLiveData<Double>(0.0)
    val mealFatCount = MutableLiveData<Double>(0.0)
    val mealCarbCount = MutableLiveData<Double>(0.0)
    val mealDate = MutableLiveData<Date>(Date())

    init {
        _addMealRepository = addMealRepository
    }

    fun add() {
        viewModelScope.launch {
            _addMealRepository.add(
                Meal(
                0,
                 mealName.value ?: "",
                mealCalorieCount.value ?: 0,
                mealProteinCount.value ?: 0.0,
                mealCarbCount.value ?: 0.0,
                mealFatCount.value ?: 0.0,
                    mealDate.value ?: Date())
            )
        }
    }

}