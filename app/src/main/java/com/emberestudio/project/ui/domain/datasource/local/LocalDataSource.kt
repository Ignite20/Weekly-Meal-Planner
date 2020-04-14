package com.emberestudio.project.ui.domain.datasource.local

import android.content.SharedPreferences

class LocalDataSource : SPDataSource {
    lateinit var sharedPreferences : SharedPreferences

    override fun <T> saveData(key: String, data: T) {

    }

    override fun <T> getData(key: String): T {
        return null as T
    }
}