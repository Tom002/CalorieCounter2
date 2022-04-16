package com.example.caloriecounter2.ui.editMeal

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.caloriecounter2.model.db.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EditMealViewModel @Inject constructor(
    editMealRepository: EditMealRepository,
    application: Application
) : AndroidViewModel(application) {

    private lateinit var _editMealRepository: EditMealRepository

    var mealMutableLiveData: MutableLiveData<Meal> = MutableLiveData()
    private val mMealr: Meal? = null



    // getter and setter methods
    // for email variable
    @Bindable
    fun getMealName(): String? {
        return meal.name
    }

    fun setMealName(name: String?) {
        meal.name = name.toString()
        notifyPropertyChanged(BR.userEmail)
    }

    fun saveChanges() {

    }
}