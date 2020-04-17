package com.emberestudio.project.ui.splash.di

import com.emberestudio.project.ui.splash.ui.SplashFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface SplashSubcomponent : AndroidInjector<SplashFragment>{

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<SplashFragment>
}