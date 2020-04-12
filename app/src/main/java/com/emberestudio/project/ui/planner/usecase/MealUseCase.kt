package com.emberestudio.project.ui.planner.usecase

import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.repository.Repository
import com.emberestudio.project.ui.domain.usecase.base.MealsUseCaseContract
import com.emberestudio.project.ui.domain.usecase.base.SingleUseCaseImplementation
import javax.inject.Inject

interface MealUseCase : MealsUseCaseContract

class MealUseCaseImplementation @Inject constructor(private val repository: Repository) :
    MealUseCase {

    override val mealResponse = SingleUseCaseImplementation<Meal>()

    override val planResponse = SingleUseCaseImplementation<MutableMap<Int, MutableList<Meal>>>()

    override val changeResponse = SingleUseCaseImplementation<Boolean>()

    override fun getMeal(day: Int, meal: Int) = repository.getMeal(day, meal, mealResponse)

    override fun getPlan() = repository.getPlan(planResponse)

    override fun saveMeal(day: Int, meal: Meal) {
        repository.saveMeal(planResponse, day, meal)
    }

    override fun updateMeal(from: IntArray, to: IntArray) {
        repository.updateMealPosition(changeResponse, from, to)
    }
}