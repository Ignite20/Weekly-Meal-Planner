package com.emberestudio.project.ui.domain.model

class Grocery {
    var list : MutableList<GroceryItem>? = mutableListOf()
}

class GroceryItem{
    var content: String? = ""
    var checked: Boolean = false
}