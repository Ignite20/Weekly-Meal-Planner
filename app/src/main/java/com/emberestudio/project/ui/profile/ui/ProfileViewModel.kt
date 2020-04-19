package com.emberestudio.project.ui.profile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.emberestudio.project.ui.base.BaseViewModel
import com.emberestudio.project.ui.managers.AuthenticationManager
import com.emberestudio.project.ui.profile.model.ProfileUiModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileViewModel @Inject constructor(private var authManager : AuthenticationManager) : BaseViewModel(){

    val uiData = MediatorLiveData<ProfileUiModel>()
    var profile : LiveData<ProfileUiModel> = uiData


}