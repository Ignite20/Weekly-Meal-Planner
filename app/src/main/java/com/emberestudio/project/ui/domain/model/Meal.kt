package com.emberestudio.project.ui.domain.model

class Meal {
    constructor()

    constructor(id : String,
                author : String,
                name : String,
                description : String,
                ingredients : List<Ingredient>,
                steps : List<Step>,
                global : Boolean
                ){
        this.id = id
        this.author = author
        this.name = name
        this.description = description
        this.ingredients = ingredients
        this.steps = steps
        this.global = global

    }
    var id : String = ""
    var author : String = ""
    var name : String = ""
    var description : String = ""
    var ingredients : List<Ingredient>? = null
    var steps : List<Step>? = null
    var global : Boolean = true
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