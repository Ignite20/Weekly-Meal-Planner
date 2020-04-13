package com.emberestudio.project.ui.profile.di

import com.emberestudio.project.ui.profile.ui.ProfileFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface ProfileSubcomponent : AndroidInjector<ProfileFragment> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<ProfileFragment>
}