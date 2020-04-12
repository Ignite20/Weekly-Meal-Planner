package com.emberestudio.project.ui.domain

import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.MealDays
import com.emberestudio.project.ui.domain.model.MealType
import com.emberestudio.project.ui.domain.model.MealsResponse
import dagger.Module
import javax.inject.Inject

@Module
class MealsDataSource @Inject constructor() : DataSource<MealsResponse, Meal> {

    override var items: MutableMap<Int, MutableList<Meal>> = mutableMapOf()

    init {
        for (day in MealDays.values()){
            items[day.ordinal] = mutableListOf(
                Meal(
                    MealType.LUNCH,
                    day.name.plus(" Plate 1"),
                    "Description plate 1"
                ),
                Meal(
                    MealType.DINNER,
                    day.name.plus(" Plate 2"),
                    "Description plate 2"
                )
            )
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

    override fun addItem(group: Int, item: Meal): Boolean? {
        return items[group]?.add(item)
    }
}