package com.emberestudio.project.ui.domain.model

data class MealsResponse (
    val day : String,
    val meals: List<MealItemResponse>
)