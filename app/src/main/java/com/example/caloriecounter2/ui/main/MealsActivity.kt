package com.example.caloriecounter2.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.caloriecounter2.databinding.ActivityMainBinding
import com.example.caloriecounter2.model.db.Meal
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MealsActivity : AppCompatActivity() {

    lateinit var mealsAdapter: MealsAdapter
    lateinit var mealsViewModel: MealsViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.setLifecycleOwner(this)

        mealsViewModel = ViewModelProvider(this).get(MealsViewModel::class.java)
        binding.viewModel = mealsViewModel

        initRecyclerView()
    }

    private fun initRecyclerView() {
        mealsAdapter = MealsAdapter(this, mealsViewModel)
        binding.listMeals.adapter = mealsAdapter
        binding.listMeals.layoutManager = LinearLayoutManager(this)

        mealsViewModel.mealList.observe(this, {
                meals -> mealsAdapter.submitList(meals)
        })
    }
}