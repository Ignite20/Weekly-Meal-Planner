package com.emberestudio.project.ui.domain.usecase.base

import com.emberestudio.project.ui.domain.model.Meal


interface MealsUseCaseContract{
    val mealResponse : SingleUseCase<Meal>

    val mealsResponse : SingleUseCase<MutableList<Meal>>

    val planResponse : SingleUseCase<MutableMap<Int, MutableList<Meal>>>

    val changeResponse : SingleUseCase<Boolean>

    fun getMeal(day: Int, meal: Int)

    fun getPlan()

    fun saveMeal(day: Int, meal: Meal)

    fun updateMeal(from : IntArray, to : IntArray)

    fun saveMeal(meal: Meal)

    fun getMeals()

    fun getMeal(uidMeal : String)

    fun removeMeal(uidMeal: String)
}