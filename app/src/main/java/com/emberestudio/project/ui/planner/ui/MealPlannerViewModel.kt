package com.emberestudio.project.ui.planner.ui

import com.emberestudio.project.ui.base.BaseViewModel
import com.emberestudio.project.ui.domain.MealsDataSource
import com.emberestudio.project.ui.planner.model.Meal
import com.emberestudio.project.ui.planner.model.MealDays
import com.emberestudio.project.ui.planner.model.MealType
import javax.inject.Inject

class MealPlannerViewModel @Inject constructor() : BaseViewModel() {

    lateinit var dataSource : MealsDataSource
    private fun createMeals() {
        dataSource = MealsDataSource()
        for (day in MealDays.values()){
            dataSource.items[day.ordinal] = listOf( Meal(MealType.LUNCH, "Plate 1"), Meal(MealType.DINNER, "Plate 2") )
        }
    }

    fun getMeals() : MutableMap<Int, List<Meal>> {
        createMeals()
        return dataSource.getMap()
    }


}