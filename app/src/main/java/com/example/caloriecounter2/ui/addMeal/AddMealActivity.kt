package com.example.caloriecounter2.ui.addMeal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.caloriecounter2.databinding.ActivityAddMealBinding

class AddMealActivity : AppCompatActivity() {

    private lateinit var addMealViewModel: AddMealViewModel
    private lateinit var binding: ActivityAddMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        addMealViewModel = ViewModelProvider(this).get(AddMealViewModel::class.java)

        binding.viewModel = addMealViewModel
        binding.lifecycleOwner = this
    }
}