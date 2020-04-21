package com.emberestudio.project.ui.domain.model

class Plan {
    constructor()

    constructor(
        id : String,
        title: String,
        planification : MutableList<DayPlan>
    ){
        this.id = id
        this.title = title
        this.author = author
        this.planification = planification
    }

    var title: String = ""
    var id : String = ""
    var author : String = ""
    var planification : MutableList<DayPlan>? = null
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
