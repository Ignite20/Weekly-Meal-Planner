package com.emberestudio.project.ui.domain.datasource.local

import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.MealsResponse
import com.emberestudio.project.ui.domain.model.WeekDays
import dagger.Module
import javax.inject.Inject

@Module
class MealsDataSource @Inject constructor() :
    DataSource<MealsResponse, Meal> {

    override var items: MutableMap<Int, MutableList<Meal>> = mutableMapOf()

    init {
        for (day in WeekDays.values()){

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

    override fun updateItemPosition(from: IntArray, to: IntArray): Boolean? {
        val item = items[from[0]]?.get(from[1])!!
        val isDeleted = deleteItem(from[0], item)
        if ( isDeleted != null && isDeleted) {
            items[to[0]]?.add(to[1],item)
            return true
        }
        return false
    }
}