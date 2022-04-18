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

        mealsAdapter.submitList(getSampleMeals())

        /*
        mealsViewModel.mealList.observe(this, {
                meals -> mealsAdapter.submitList(meals)
        })
         */
    }

    fun getSampleMeals(): List<Meal> {
        return listOf(
            Meal(1, "Paprikás krumpli", 600, 50.1, 50.2, 10.3, Date(2022, 9, 10)),
            Meal(2, "Csirk rizs", 600, 70.0, 40.0, 10.0, Date(2022, 9, 10)),
        )
    }
}