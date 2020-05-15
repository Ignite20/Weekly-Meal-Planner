package com.emberestudio.project.ui.domain.repository

import com.emberestudio.project.ui.domain.api.ApiCallback
import com.emberestudio.project.ui.domain.datasource.firebase.FireBaseDataSource
import com.emberestudio.project.ui.domain.datasource.firebase.FireBaseDataSourceImpl
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.Plan
import com.emberestudio.project.ui.domain.usecase.error.Error
import com.emberestudio.project.ui.managers.AuthenticationManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val fireBaseDataSource: FireBaseDataSourceImpl,
    private val authManager : AuthenticationManager
    ){

    //FIREBASE
    fun getPlan(callback: ApiCallback<Plan, Error>, planId : String){
        fireBaseDataSource.getPlan(planId, object : FireBaseDataSource.OnPlanRetrieved{
            override fun onSuccess(plan: Plan) {
                callback.onResponse("", plan)
            }
        })
    }

    fun savePlan(callback: ApiCallback<String, Error>, plan: Plan){
        fireBaseDataSource.updatePlan(plan, object : FireBaseDataSource.OnPlanSaved{
            override fun onSuccess(planId: String) {
                callback.onResponse("", planId)
            }
        })
    }

    fun removePlanification(callback: ApiCallback<MutableList<Plan>, Error>, planId: String){
        fireBaseDataSource.removePlanification(planId, object : FireBaseDataSource.OnPlanificationsRetrieved{
            override fun onSuccess(list: MutableList<Plan>) {
                callback.onResponse("", list)
            }
        })
    }

    fun getPlanifications(callback: ApiCallback<MutableList<Plan>, Error>){
        fireBaseDataSource.getPlanifications(object : FireBaseDataSource.OnPlanificationsRetrieved{
            override fun onSuccess(list: MutableList<Plan>) {
                callback.onResponse("", list)
            }
        })
    }

    fun savePlanification(plan: Plan, callback: ApiCallback<String, Error>){
        fireBaseDataSource.savePlanification(plan, object : FireBaseDataSource.OnPlanSaved{
            override fun onSuccess(planId: String) {
                callback.onResponse("", planId)
            }
        })
    }

    fun getCurrentUser(callback: ApiCallback<Boolean, Error>){
        callback.onResponse("",authManager.firebaseAuth?.currentUser != null)
    }

    fun saveMeal(callback: ApiCallback<MutableList<Meal>, Error>, meal: Meal){
        fireBaseDataSource.saveMeal(meal, object : FireBaseDataSource.FireBaseListener{
            override fun onItemSaved(item: Meal?) {
            }

            override fun onMealsResponse(list: MutableList<Meal>?) {
                callback.onResponse("", list)
            }
        })
    }

    fun getMeals(callback: ApiCallback<MutableList<Meal>, Error>){
        fireBaseDataSource.getMeals(object : FireBaseDataSource.FireBaseListener{
            override fun onItemSaved(item: Meal?) {

            }

            override fun onMealsResponse(list: MutableList<Meal>?) {
                callback.onResponse("", list)
            }
        })
    }

    fun getMeal(callback: ApiCallback<Meal, Error>, uidMeal : String){
        fireBaseDataSource.getMeal(uidMeal, object : FireBaseDataSource.FireBaseListener{
            override fun onItemSaved(item: Meal?) {
                callback.onResponse("", item)
            }

            override fun onMealsResponse(list: MutableList<Meal>?) {

            }
        })
    }

    fun removeMeal(callback: ApiCallback<MutableList<Meal>, Error>, idMeal : String){
        fireBaseDataSource.removeMeal(idMeal, object : FireBaseDataSource.OnItemRemoved{
            override fun onItemRemoved(item: String) {
                getMeals(callback)
            }
        })
    }


}