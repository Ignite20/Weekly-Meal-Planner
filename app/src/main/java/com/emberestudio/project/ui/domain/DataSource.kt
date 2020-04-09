package com.emberestudio.project.ui.domain

interface DataSource<Response, DomainItem>{

    var items : MutableMap<Int, MutableList<DomainItem>>

    fun getGroupItems(position: Int) : MutableList<DomainItem>?

    fun getChildItem(group : Int, child : Int) : DomainItem?

    fun deleteItem(group: Int, item : DomainItem) : Boolean?

    fun updateItem(group: Int, item : DomainItem) : Boolean?

    fun getMap() : MutableMap<Int, MutableList<DomainItem>>?


}