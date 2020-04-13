package com.emberestudio.project.ui.profile.model

data class ProfileUiModel (
    val username : String,
    val photoUrl : String,
    var logged : Boolean = false
)