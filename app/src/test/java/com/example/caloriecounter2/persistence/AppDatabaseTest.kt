package com.example.caloriecounter2.persistence

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.caloriecounter2.model.db.Meal
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class AppDatabaseTest {

    private lateinit var mealDao: MealDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            getApplicationContext(), AppDatabase::class.java
        ).allowMainThreadQueries().build()
        mealDao = db.mealDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun shouldAddMeal() = runBlocking {
        val mealToAdd = Meal(1,"Test meal", 500, 40.0, 50.0, 10.0, 2022, 5, 18)
        val addedMealId = mealDao.insertMeal(mealToAdd)

        val mealAfterInsert = mealDao.getMeal(addedMealId)

        assertNotNull(mealAfterInsert)
        assertEquals(mealToAdd.calories, mealAfterInsert!!.calories)
        assertEquals(mealToAdd.name, mealAfterInsert.name)
        assertEquals(mealToAdd.fatInGrams, mealAfterInsert.fatInGrams, 0.001)
        assertEquals(mealToAdd.proteinInGrams, mealAfterInsert.proteinInGrams, 0.001)
        assertEquals(mealToAdd.carbInGrams, mealAfterInsert.carbInGrams, 0.001)

        val mealListAfterInsert = mealDao.getMealListForDate(2022, 5, 18)
        assertNotNull(mealListAfterInsert)
        assert(mealListAfterInsert.size == 1)
        assertEquals(mealListAfterInsert[0].id, mealAfterInsert.id)
        assertEquals(mealListAfterInsert[0].calories, mealAfterInsert.calories)
        assertEquals(mealListAfterInsert[0].name, mealAfterInsert.name)
        assertEquals(mealListAfterInsert[0].fatInGrams, mealAfterInsert.fatInGrams, 0.001)
        assertEquals(mealListAfterInsert[0].proteinInGrams, mealAfterInsert.proteinInGrams, 0.001)
        assertEquals(mealListAfterInsert[0].carbInGrams, mealAfterInsert.carbInGrams, 0.001)
    }

    @Test
    fun shouldDeleteMeal() = runBlocking {
        val mealToAdd = Meal(1,"Test meal", 500, 40.0, 50.0, 10.0, 2022, 5, 18)
        val addedMealId = mealDao.insertMeal(mealToAdd)
        val mealAfterInsert = mealDao.getMeal(addedMealId)

        assertNotNull(mealAfterInsert)
        assertEquals(mealToAdd.calories, mealAfterInsert!!.calories)
        assertEquals(mealToAdd.name, mealAfterInsert!!.name)
        assertEquals(mealToAdd.fatInGrams, mealAfterInsert!!.fatInGrams, 0.001)
        assertEquals(mealToAdd.proteinInGrams, mealAfterInsert!!.proteinInGrams, 0.001)
        assertEquals(mealToAdd.carbInGrams, mealAfterInsert!!.carbInGrams, 0.001)

        val mealListAfterInsert = mealDao.getMealListForDate(2022, 5, 18)
        assertNotNull(mealListAfterInsert)
        assert(mealListAfterInsert.size == 1)
        assertEquals(mealListAfterInsert[0].id, mealAfterInsert.id)
        assertEquals(mealListAfterInsert[0].calories, mealAfterInsert.calories)
        assertEquals(mealListAfterInsert[0].name, mealAfterInsert.name)
        assertEquals(mealListAfterInsert[0].fatInGrams, mealAfterInsert.fatInGrams, 0.001)
        assertEquals(mealListAfterInsert[0].proteinInGrams, mealAfterInsert.proteinInGrams, 0.001)
        assertEquals(mealListAfterInsert[0].carbInGrams, mealAfterInsert.carbInGrams, 0.001)

        mealDao.deleteMeal(mealAfterInsert)
        val getDeletedMealResult = mealDao.getMeal(addedMealId)
        assertNull(getDeletedMealResult)

        val mealListAfterDelete = mealDao.getMealListForDate(2022, 5, 18)
        assertTrue(mealListAfterDelete.isEmpty())
    }

    @Test
    fun shouldUpdateMeal() = runBlocking {
        val mealToAdd = Meal(1,"Test meal", 500, 40.0, 50.0, 10.0, 2022, 5, 18)
        val addedMealId = mealDao.insertMeal(mealToAdd)
        val mealAfterInsert = mealDao.getMeal(addedMealId)

        assertNotNull(mealAfterInsert)
        assertEquals(mealToAdd.calories, mealAfterInsert!!.calories)
        assertEquals(mealToAdd.name, mealAfterInsert.name)
        assertEquals(mealToAdd.fatInGrams, mealAfterInsert.fatInGrams, 0.001)
        assertEquals(mealToAdd.proteinInGrams, mealAfterInsert.proteinInGrams, 0.001)
        assertEquals(mealToAdd.carbInGrams, mealAfterInsert.carbInGrams, 0.001)

        val mealListAfterInsert = mealDao.getMealListForDate(2022, 5, 18)
        assertNotNull(mealListAfterInsert)
        assert(mealListAfterInsert.size == 1)
        assertEquals(mealListAfterInsert[0].id, mealToAdd.id)
        assertEquals(mealListAfterInsert[0].calories, mealToAdd.calories)
        assertEquals(mealListAfterInsert[0].name, mealToAdd.name)
        assertEquals(mealListAfterInsert[0].fatInGrams, mealToAdd.fatInGrams, 0.001)
        assertEquals(mealListAfterInsert[0].proteinInGrams, mealToAdd.proteinInGrams, 0.001)
        assertEquals(mealListAfterInsert[0].carbInGrams, mealToAdd.carbInGrams, 0.001)

        val mealUpdate = Meal(addedMealId,"Updated meal", 600, 50.0, 60.0, 20.0, 2023, 6, 19)
        mealDao.updateMeal(mealUpdate)

        val mealAfterUpdate = mealDao.getMeal(addedMealId)
        assertNotNull(mealAfterUpdate)
        assertEquals(mealUpdate.calories, mealAfterUpdate!!.calories)
        assertEquals(mealUpdate.name, mealAfterUpdate.name)
        assertEquals(mealUpdate.fatInGrams, mealAfterUpdate.fatInGrams, 0.001)
        assertEquals(mealUpdate.proteinInGrams, mealAfterUpdate.proteinInGrams, 0.001)
        assertEquals(mealUpdate.carbInGrams, mealAfterUpdate.carbInGrams, 0.001)

        val mealListAfterUpdate = mealDao.getMealListForDate(2023, 6, 19)
        assertNotNull(mealListAfterInsert)
        assert(mealListAfterUpdate.size == 1)
        assertEquals(mealListAfterUpdate[0].calories, mealUpdate.calories)
        assertEquals(mealListAfterUpdate[0].name, mealUpdate.name)
        assertEquals(mealListAfterUpdate[0].fatInGrams, mealUpdate.fatInGrams, 0.001)
        assertEquals(mealListAfterUpdate[0].proteinInGrams, mealUpdate.proteinInGrams, 0.001)
        assertEquals(mealListAfterUpdate[0].carbInGrams, mealUpdate.carbInGrams, 0.001)
    }

    @Test
    fun shouldListMealsForDate() = runBlocking {
        val mealToAdd1 = Meal(1,"Test meal", 500, 40.0, 50.0, 10.0, 2022, 5, 18)
        mealDao.insertMeal(mealToAdd1)

        val mealToAdd2 = Meal(2,"Test meal 2", 500, 40.0, 50.0, 10.0, 2022, 5, 18)
        mealDao.insertMeal(mealToAdd2)

        val mealToAdd3 = Meal(3,"Test meal 3", 500, 40.0, 50.0, 10.0, 2022, 5, 19)
        mealDao.insertMeal(mealToAdd3)

        val mealListAfterInserts = mealDao.getMealListForDate(2022, 5, 18)
        assertNotNull(mealListAfterInserts)
        assert(mealListAfterInserts.size == 2)

        assertTrue(mealListAfterInserts.contains(
            Meal(
                mealToAdd1.id,
                mealToAdd1.name,
                mealToAdd1.calories,
                mealToAdd1.proteinInGrams,
                mealToAdd1.carbInGrams,
                mealToAdd1.fatInGrams,
                mealToAdd1.year,
                mealToAdd1.month,
                mealToAdd1.day)))

        assertTrue(mealListAfterInserts.contains(
            Meal(
                mealToAdd2.id,
                mealToAdd2.name,
                mealToAdd2.calories,
                mealToAdd2.proteinInGrams,
                mealToAdd2.carbInGrams,
                mealToAdd2.fatInGrams,
                mealToAdd2.year,
                mealToAdd2.month,
                mealToAdd2.day)))

        assertFalse(mealListAfterInserts.contains(
            Meal(
                mealToAdd3.id,
                mealToAdd3.name,
                mealToAdd3.calories,
                mealToAdd3.proteinInGrams,
                mealToAdd3.carbInGrams,
                mealToAdd3.fatInGrams,
                mealToAdd3.year,
                mealToAdd3.month,
                mealToAdd3.day)))
    }
}