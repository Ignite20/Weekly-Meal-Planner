package com.emberestudio.project.ui.domain.datasource.local

interface SPDataSource {

    fun <T> saveData(key : String, data : T)

    fun <T> getData(key: String) : T
}