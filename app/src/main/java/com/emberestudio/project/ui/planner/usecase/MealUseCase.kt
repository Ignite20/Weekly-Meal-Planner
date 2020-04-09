package com.emberestudio.project.ui.planner.usecase

import com.emberestudio.project.ui.domain.repository.Repository
import com.emberestudio.project.ui.domain.usecase.base.MealsUseCaseContract
import com.emberestudio.project.ui.domain.usecase.base.SingleUseCaseImplementation
import com.emberestudio.project.ui.planner.model.Meal
import javax.inject.Inject

interface MealUseCase : MealsUseCaseContract

class MealUseCaseImplementation @Inject constructor(private val repository: Repository) :
    MealUseCase {

    override val mealResponse = SingleUseCaseImplementation<Meal>()

    override val planResponse = SingleUseCaseImplementation<MutableMap<Int, MutableList<Meal>>>()

    override fun getMeal(day: Int, meal: Int) = repository.getMeal(day, meal, mealResponse)

    override fun getPlan() = repository.getPlan(planResponse)
}