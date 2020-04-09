package com.emberestudio.project.ui.domain.repository

import com.emberestudio.project.ui.domain.MealsDataSource
import com.emberestudio.project.ui.domain.api.ApiCallback
import com.emberestudio.project.ui.domain.usecase.error.Error
import com.emberestudio.project.ui.planner.model.Meal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val dataSource: MealsDataSource){

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
}