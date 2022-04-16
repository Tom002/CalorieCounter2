package com.example.caloriecounter2.ui.editMeal

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.ui.AppBarConfiguration
import com.example.caloriecounter2.R
import com.example.caloriecounter2.databinding.ActivityEditMealBinding
import com.example.caloriecounter2.ui.main.MealsViewModel

class EditMealActivity : AppCompatActivity() {

    companion object {
        const val KEY_NAME = "KEY_NAME";
        const val KEY_CALORIES = "KEY_CALORIES";
        const val KEY_CARBS = "KEY_CARBS";
        const val KEY_PROTEIN = "KEY_PROTEIN";
        const val KEY_FAT = "KEY_FAT";
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityEditMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    lateinit var editMealViewModel: EditMealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_meal)

        editMealViewModel = ViewModelProvider(this).get(EditMealViewModel::class.java)
    }
}