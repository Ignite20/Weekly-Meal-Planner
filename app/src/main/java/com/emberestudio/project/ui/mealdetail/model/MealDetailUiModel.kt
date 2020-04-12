package com.emberestudio.project.ui.mealdetail.model

import com.emberestudio.project.ui.domain.model.Meal

data class MealDetailUiModel (
    val name : String,
    val description : String,
    val type : String
)

fun Meal.toUiModel() : MealDetailUiModel{
    return MealDetailUiModel(name, description, type.name)
}