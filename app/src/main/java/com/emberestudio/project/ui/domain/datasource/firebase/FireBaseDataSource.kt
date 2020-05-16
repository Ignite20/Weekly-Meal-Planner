package com.emberestudio.project.ui.domain.datasource.firebase

import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.Plan

interface FireBaseDataSource {
    interface FireBaseListener {
        fun onItemSaved(item : Meal?)

        fun onMealsResponse(list : MutableList<Meal>?)
    }

    interface OnPlanificationsRetrieved{
        fun onSuccess(list : MutableList<Plan>)
    }

    interface OnPlanRetrieved{
        fun onSuccess(plan : Plan)
    }

    interface OnPlanSaved{
        fun onSuccess(planId : String)
    }

    interface OnItemRemoved{
        fun onItemRemoved(item : String)
    }

    // DAYSPLAN
    fun getPlan(planId: String, listener: OnPlanRetrieved?)

    fun updatePlan(plan: Plan, listener: OnPlanSaved?)


    // PLANIFICATION
    fun removePlanification(planId: String, listener: OnPlanificationsRetrieved?)

    fun savePlanification(plan: Plan, listener : OnPlanSaved?)

    fun getPlanifications(listener : OnPlanificationsRetrieved?)

    fun updatePlanification(plan: Plan, listener: OnPlanificationsRetrieved?)

    // MEAL
    fun saveMeal(meal : Meal, listener : FireBaseListener?)

    fun getMeals(listener : FireBaseListener?)

    fun getMeal(id : String, listener : FireBaseListener?)

    fun removeMeal(id : String, listener: OnItemRemoved?)

}