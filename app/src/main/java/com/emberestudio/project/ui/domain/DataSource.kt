package com.emberestudio.project.ui.domain

import com.emberestudio.project.ui.planner.model.Meal


interface DataSource<Response, DomainItem>{

    var items : MutableMap<Int, List<DomainItem>>

    fun getItem(position: Int)

    fun getItem(key : String) : List<DomainItem>

    fun addItem(key : String, item : Response)

    fun getItems(position: Int) : List<DomainItem>?

    fun getMap() : MutableMap<Int, List<DomainItem>>

    fun removeItem(item : Response)

    fun removeItemWithKey(key : String)
}