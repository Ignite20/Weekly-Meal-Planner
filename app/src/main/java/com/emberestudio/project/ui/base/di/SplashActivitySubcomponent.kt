package com.emberestudio.project.ui.base.di

import com.emberestudio.project.ui.main.SplashActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface SplashActivitySubcomponent : AndroidInjector<SplashActivity> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<SplashActivity>
}