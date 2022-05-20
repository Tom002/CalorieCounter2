package com.example.caloriecounter2.ui.editMeal

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.caloriecounter2.model.db.Meal
import com.example.caloriecounter2.ui.main.MealsActivity
import com.example.caloriecounter2.ui.main.MealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject


@HiltViewModel
class EditMealViewModel @Inject constructor(
    mealsRepository: MealsRepository,
    application: Application
) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val _mealsRepository: MealsRepository
    private lateinit var mealToEdit: Meal

    var mealName = MutableLiveData<String>("")
    var mealCalorieCount = MutableLiveData<Int>(0)
    var mealProteinCount = MutableLiveData<Double>(0.0)
    var mealFatCount = MutableLiveData<Double>(0.0)
    var mealCarbCount = MutableLiveData<Double>(0.0)

    init {
        _mealsRepository = mealsRepository
    }

    fun update() {
        viewModelScope.launch {
            try {
                _mealsRepository.update(
                    Meal(
                        mealToEdit.id,
                        mealName.value ?: "",
                        mealCalorieCount.value ?: 0,
                        mealProteinCount.value ?: 0.0,
                        mealCarbCount.value ?: 0.0,
                        mealFatCount.value ?: 0.0,
                        mealToEdit.year,
                        mealToEdit.month,
                        mealToEdit.day)
                )
                val toast = Toast.makeText(context, "Meal updated successfully", Toast.LENGTH_LONG)
                toast.show()
            }
            catch (e: HttpException)
            {
                val toast = Toast.makeText(context, "Unexpected error while updating meal", Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

    fun loadMealToEdit(mealId: Long) {
        viewModelScope.launch {
            mealToEdit = _mealsRepository.getMealById(mealId);
            mealName.value = mealToEdit.name
            mealCalorieCount.value = mealToEdit.calories
            mealProteinCount.value = mealToEdit.proteinInGrams
            mealCarbCount.value = mealToEdit.carbInGrams
            mealFatCount.value = mealToEdit.fatInGrams
        }
    }

    fun back() {
        val intent = Intent(context, MealsActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent)
    }
}