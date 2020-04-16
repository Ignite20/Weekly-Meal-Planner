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
    var order : Int = 0,
    var description: String = ""
) : Item()

data class Ingredient(
    var name : String = "",
    var quantity : String = "",
    var unit : String = ""
) : Item()

abstract class Item

enum class QuantityUnit(val longName : String, val shortName : String){
    NO_UNIT ("",""),
    KILOGRAMS("Kilograms", "Kg"),
    GRAMS("grams", "g"),
    LITERS("Liters", "l"),
    MILLILITERS("Milliliters", "ml"),
    PIECES("Pieces", "pcs");


    companion object {
        fun shortNames() : List<String> {
            return values().map { it.shortName }
        }

        fun longNames() : List<String> {
            return values().map { it.longName }
        }

        fun findValue(value : String) : QuantityUnit? {
            return values().find { it.longName == value || it.shortName == value }
        }
    }
}