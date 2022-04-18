package com.example.caloriecounter2.ui.editMeal

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.caloriecounter2.model.db.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class EditMealViewModel @Inject constructor(
    editMealRepository: EditMealRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _editMealRepository: EditMealRepository
    // private lateinit var mealToEdit: Meal
    private var mealToEdit = Meal(1, "Teszt étkezés", 500, 50.0, 50.0, 10.0, Date(2022,4,15))

    lateinit var mealName: MutableLiveData<String>
    lateinit var mealCalorieCount: MutableLiveData<Int>
    lateinit var mealProteinCount: MutableLiveData<Double>
    lateinit var mealFatCount: MutableLiveData<Double>
    lateinit var mealCarbCount: MutableLiveData<Double>

    init {
        _editMealRepository = editMealRepository
        viewModelScope.launch {
            // TODO: Id átadás lista activity-ből
            // mealToEdit = _editMealRepository.getMealById(1);
            mealName.value = mealToEdit.name
            mealCalorieCount.value = mealToEdit.calories
            mealProteinCount.value = mealToEdit.proteinInGrams
            mealCarbCount.value = mealToEdit.carbInGrams
            mealFatCount.value = mealToEdit.fatInGrams
        }
    }

    fun update() {
        viewModelScope.launch {
            _editMealRepository.update(Meal(
                mealToEdit.id,
                mealName.value ?: "",
                mealCalorieCount.value ?: 0,
                mealProteinCount.value ?: 0.0,
                mealCarbCount.value ?: 0.0,
                mealFatCount.value ?: 0.0,
                mealToEdit.date))
        }
    }
}