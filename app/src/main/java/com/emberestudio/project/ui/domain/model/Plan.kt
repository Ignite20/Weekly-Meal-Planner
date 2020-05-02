package com.emberestudio.project.ui.domain.model

class Plan {
    constructor()

    constructor(
        title: String = "",
        planification : MutableList<DayPlan>,
        roles : MutableMap<String, String>
    ){
        this.title = title
        this.planification = planification
        this.roles = roles
    }

    var title: String = ""
    var id : String = ""
    var planification : MutableList<DayPlan> = mutableListOf()
    var roles : MutableMap<String, String>? = mutableMapOf()
}

class DayPlan {
    constructor()

    constructor(
        day: String,
        meals: MutableList<MealSnapshot>
    ){
        this.day = day
        this.meals = meals
    }
    var day: String = ""
    var meals: MutableList<MealSnapshot> = mutableListOf()
}

class MealSnapshot{
    constructor()

    constructor(
        title: String,
        mealId: String
    ){
        this.title = title
        this.mealId = mealId
    }

    var title : String = ""
    var mealId : String = ""
}
