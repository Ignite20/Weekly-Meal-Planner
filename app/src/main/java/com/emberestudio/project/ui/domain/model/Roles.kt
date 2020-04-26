package com.emberestudio.project.ui.domain.model

enum class Roles(var nName: String) {
    OWNER("owner"),
    COLLABORATOR("collaborator");

    companion object{
        fun getNameValues() : List<String>{
            return values().map { it.nName }
        }
    }
}