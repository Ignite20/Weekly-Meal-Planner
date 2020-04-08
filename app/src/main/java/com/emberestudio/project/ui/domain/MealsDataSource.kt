package com.emberestudio.project.ui.domain

import com.emberestudio.project.ui.domain.model.MealsResponse
import com.emberestudio.project.ui.planner.model.Meal

class MealsDataSource : DataSource<MealsResponse, Meal> {

    override var items: MutableMap<Int, List<Meal>> = mutableMapOf()

    override fun getItem(position: Int) {
        TODO("Not yet implemented")
    }

    override fun addItem(key: String, item: MealsResponse) {
        TODO("Not yet implemented")
    }

    fun populateItems(key: String, items: List<Meal>){

    }

    override fun getItems(position: Int): List<Meal>? {
        TODO("Not yet implemented")
    }

    override fun getMap(): MutableMap<Int, List<Meal>> {
        return items
    }

    override fun removeItem(item: MealsResponse) {
        TODO("Not yet implemented")
    }

    override fun removeItemWithKey(key: String) {
        TODO("Not yet implemented")
    }

    override fun getItem(key: String): List<Meal> {
        TODO("Not yet implemented")
    }


}