package com.emberestudio.project.ui.mealdetail.model

import com.emberestudio.project.ui.domain.model.Ingredient
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.Step

data class MealDetailUiModel (
    val name : String,
    val description : String,
    val ingredients : List<Ingredient>,
    val steps : List<Step>
)

fun Meal.toUiModel() : MealDetailUiModel{
    return MealDetailUiModel(
        name = name,
        description = description,
        ingredients = ingredients.orEmpty(),
        steps = steps.orEmpty()
    )
}