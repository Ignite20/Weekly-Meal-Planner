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
    var planification : MutableList<DayPlan>? = null
    var roles : MutableMap<String, String>? = null
}

class DayPlan {
    constructor()

    constructor(
        day: String,
         meals: MutableList<Meal>
    ){
        this.day = day
        this.meals = meals
    }
    var day: String = ""
    var meals: MutableList<Meal>? = null

}
