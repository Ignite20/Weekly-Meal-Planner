package com.emberestudio.project.ui.domain.repository

import com.emberestudio.project.ui.domain.api.ApiCallback
import com.emberestudio.project.ui.domain.datasource.firebase.FireBaseDataSource
import com.emberestudio.project.ui.domain.datasource.firebase.FireBaseDataSourceImpl
import com.emberestudio.project.ui.domain.datasource.local.MealsDataSource
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.Plan
import com.emberestudio.project.ui.domain.usecase.error.Error
import com.emberestudio.project.ui.managers.AuthenticationManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val dataSource: MealsDataSource,
    private val fireBaseDataSource: FireBaseDataSourceImpl,
    private val authManager : AuthenticationManager
    ){

    fun getMeal(day: Int, meal: Int, callback: ApiCallback<Meal, Error>){
        dataSource.getChildItem(day, meal)?.let {
            callback.onResponse("", it)
        }
    }

    fun getPlan(callback: ApiCallback<MutableMap<Int, MutableList<Meal>>, Error>){
        dataSource.getMap().let {
            callback.onResponse("", it)
        }
    }

    fun saveMealLocal(callback: ApiCallback<MutableMap<Int, MutableList<Meal>>, Error>, day: Int, item : Meal){
        dataSource.addItem(day, item)
    }

    fun updateMealPosition(callback : ApiCallback<Boolean, Error>, from : IntArray, to : IntArray){
        dataSource.updateItemPosition(from, to)
    }
    //FIREBASE

    fun getPlanifications(callback: ApiCallback<MutableList<Plan>, Error>){
        fireBaseDataSource.getPlanifications(object : FireBaseDataSource.OnPlanificationsRetrieved{
            override fun onSuccess(list: MutableList<Plan>) {
                callback.onResponse("", list)
            }
        })
    }

    fun savePlanification(plan: Plan, callback: ApiCallback<MutableList<Plan>, Error>){
        fireBaseDataSource.savePlanification(plan, object : FireBaseDataSource.OnPlanificationsRetrieved{
            override fun onSuccess(list: MutableList<Plan>) {
                callback.onResponse("", list)
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