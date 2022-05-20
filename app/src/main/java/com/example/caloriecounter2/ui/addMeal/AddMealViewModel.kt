package com.example.caloriecounter2.ui.addMeal

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.caloriecounter2.model.db.Meal
import com.example.caloriecounter2.ui.main.MealsActivity
import com.example.caloriecounter2.ui.main.MealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddMealViewModel @Inject constructor(
    mealsRepository: MealsRepository,
    application: Application
) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val _mealsRepository: MealsRepository

    val mealName = MutableLiveData<String>()
    val mealCalorieCount = MutableLiveData<Int>(0)
    val mealProteinCount = MutableLiveData<Double>(0.0)
    val mealFatCount = MutableLiveData<Double>(0.0)
    val mealCarbCount = MutableLiveData<Double>(0.0)
    val mealDate = MutableLiveData<Date>(mealsRepository.selectedDate.value!!.toDate())

    init {
        _mealsRepository = mealsRepository
    }

    fun add() {
        viewModelScope.launch {
            try {
                var date = DateTime(mealDate.value)
                _mealsRepository.add(
                    Meal(0,
                        mealName.value ?: "",
                        mealCalorieCount.value ?: 0,
                        mealProteinCount.value ?: 0.0,
                        mealCarbCount.value ?: 0.0,
                        mealFatCount.value ?: 0.0,
                        date.year,
                        date.monthOfYear,
                        date.dayOfMonth))
                val toast = Toast.makeText(context, "Meal added successfully", Toast.LENGTH_LONG)
                toast.show()
            }
            catch (e: HttpException)
            {
                val toast = Toast.makeText(context, "Unexpected error while adding meal", Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

    fun back() {
        val intent = Intent(context, MealsActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent)
    }
}