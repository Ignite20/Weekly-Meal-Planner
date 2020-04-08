package com.emberestudio.project.ui.base.di

import com.emberestudio.project.ui.main.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Dagger [Subcomponent] that provides[Subcomponent.Factory] to create [MainActivity]
 */
@Subcomponent
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>
}
