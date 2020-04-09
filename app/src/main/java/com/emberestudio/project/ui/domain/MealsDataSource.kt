package com.emberestudio.project.ui.domain

import com.emberestudio.project.ui.domain.model.MealsResponse
import com.emberestudio.project.ui.planner.model.Meal
import com.emberestudio.project.ui.planner.model.MealDays
import com.emberestudio.project.ui.planner.model.MealType
import dagger.Module
import javax.inject.Inject

@Module
class MealsDataSource @Inject constructor() : DataSource<MealsResponse, Meal> {

    override var items: MutableMap<Int, MutableList<Meal>> = mutableMapOf()

    init {
        for (day in MealDays.values()){
            items[day.ordinal] = mutableListOf( Meal( MealType.LUNCH, "Plate 1"), Meal(MealType.DINNER, "Plate 2") )
        }
    }

    override fun getGroupItems(position: Int): MutableList<Meal>? {
        return items[position]
    }

    override fun getChildItem(group: Int, child: Int): Meal? {
        return items[group]?.get(child) as Meal
    }

    override fun getMap(): MutableMap<Int, MutableList<Meal>> {
        return items
    }

    override fun deleteItem(group: Int, item: Meal) : Boolean? {
        return items[group]?.remove(item)
    }

    override fun updateItem(group: Int, item: Meal): Boolean? {
        val isDeleted = deleteItem(group, item)
        if ( isDeleted != null && isDeleted) {
            return items[group]?.add(item)
        }
        return false
    }
}