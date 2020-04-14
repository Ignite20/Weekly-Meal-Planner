package com.emberestudio.project.ui.domain.model

class Meal {
    constructor()

    constructor(id : String,
                name : String,
                description : String,
                ingredients : List<Ingredient>,
                steps : List<Step>){
        this.id = id
        this.name = name
        this.description = description
        this.ingredients = ingredients
        this.steps = steps
    }
    var id : String = ""
    var name : String = ""
    var description : String = ""
    var ingredients : List<Ingredient>? = null
    var steps : List<Step>? = null
}

data class Step(
    val order : Int = 1,
    val description: String = ""
)

data class Ingredient(
    val name : String = "",
    val quantity : Int = 1,
    val unit : QuantityUnit = QuantityUnit.KILOGRAMS
)

enum class QuantityUnit(name : String, shortName : String){
    KILOGRAMS("Kilograms", "Kg"),
    LITERS("Liters", "l"),
    MILLILITERS("Milliliters", "ml")
}