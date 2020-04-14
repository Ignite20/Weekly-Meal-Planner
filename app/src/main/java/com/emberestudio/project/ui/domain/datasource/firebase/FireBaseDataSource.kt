package com.emberestudio.project.ui.domain.datasource.firebase

import com.emberestudio.project.ui.domain.model.Meal
import com.google.firebase.firestore.QuerySnapshot

interface FireBaseDataSource {
    interface FireBaseListener {
        fun onItemSaved(item : Meal?)

        fun onMealsResponse(list : MutableList<Meal>?)
    }

    interface OnItemRemoved{
        fun onItemRemoved(item : String)
    }
    fun getCurrentUser() : QuerySnapshot?

    fun saveUser(uuid: String) : Boolean?

    fun saveMeal(meal : Meal, listener : FireBaseListener?)

    fun getMeals(listener : FireBaseListener?)

    fun getMeal(id : String, listener : FireBaseListener?)

    fun removeMeal(id : String, listener: OnItemRemoved?)

}