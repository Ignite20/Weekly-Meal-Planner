package com.emberestudio.project.ui.domain.model

class User {
    constructor()

    constructor(
        uuid : String,
        name : String,
        email : String,
        plansIds : MutableList<String>
    ){
        this.uuid = uuid
        this.name = name
        this.email = email
        this.plansIds = plansIds
    }

    var name : String = ""
    var uuid : String = ""
    var email : String = ""
    var plansIds : MutableList<String>? = null

}