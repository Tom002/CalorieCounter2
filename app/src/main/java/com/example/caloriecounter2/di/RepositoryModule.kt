package com.example.caloriecounter2.di

import com.example.caloriecounter2.network.MealService
import com.example.caloriecounter2.persistence.MealDao
import com.example.caloriecounter2.ui.addMeal.AddMealRepository
import com.example.caloriecounter2.ui.editMeal.EditMealRepository
import com.example.caloriecounter2.ui.main.MealsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        mealService: MealService,
        mealDao: MealDao
    ): MealsRepository {
        return MealsRepository(mealService, mealDao)
    }

    @Provides
    @Singleton
    fun provideEditMealRepository(
        mealService: MealService,
        mealDao: MealDao
    ): EditMealRepository {
        return EditMealRepository(mealService, mealDao)
    }

    @Provides
    @Singleton
    fun provideAddMealRepository(
        mealService: MealService,
        mealDao: MealDao
    ): AddMealRepository {
        return AddMealRepository(mealService, mealDao)
    }
}