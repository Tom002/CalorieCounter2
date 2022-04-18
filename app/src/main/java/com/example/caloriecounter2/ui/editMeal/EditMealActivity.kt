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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditMealActivity : AppCompatActivity() {

    companion object {
        const val KEY_ID = "KEY_ID";
    }

    private lateinit var binding: ActivityEditMealBinding
    lateinit var editMealViewModel: EditMealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        editMealViewModel = ViewModelProvider(this).get(EditMealViewModel::class.java)

        binding.viewModel = editMealViewModel
        binding.lifecycleOwner = this
    }
}