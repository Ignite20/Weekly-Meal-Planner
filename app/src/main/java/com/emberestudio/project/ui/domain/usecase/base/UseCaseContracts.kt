package com.emberestudio.project.ui.domain.usecase.base

import com.emberestudio.project.ui.planner.model.Meal


interface MealsUseCaseContract{
    val mealResponse : SingleUseCase<Meal>

    val planResponse : SingleUseCase<MutableMap<Int, MutableList<Meal>>>

    fun getMeal(day: Int, meal: Int)

    fun getPlan()
}