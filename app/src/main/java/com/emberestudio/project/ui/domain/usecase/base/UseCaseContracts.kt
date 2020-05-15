package com.emberestudio.project.ui.domain.usecase.base

import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.Plan


interface MealsUseCaseContract{
    val mealResponse : SingleUseCase<Meal>

    val mealsResponse : SingleUseCase<MutableList<Meal>>

    val planResponse : SingleUseCase<MutableMap<Int, MutableList<Meal>>>

    val changeResponse : SingleUseCase<Boolean>

    fun saveMeal(meal: Meal)

    fun getMeals()

    fun getMeal(uidMeal : String)

    fun removeMeal(uidMeal: String)
}

interface PlanificationsUseCaseContract{
    val plansResponse : SingleUseCase<MutableList<Plan>>
    val saveResponse : SingleUseCase<String>

    fun getPlanifications()

    fun savePlanification(plan: Plan)
}

interface PlanUseCaseContract{
    val planResponse : SingleUseCase<Plan>
    val saveResponse : SingleUseCase<String>

    fun getPlan(planId: String)

    fun savePlan(plan : Plan)
}

interface UserUseCaseContract{
    val userResponse : SingleUseCase<Boolean>

    fun getCurrentUser()
}