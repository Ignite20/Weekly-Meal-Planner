package com.emberestudio.project.ui.base.di

import androidx.lifecycle.ViewModelProvider
import com.emberestudio.project.ui.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

/**
 * Dagger [Module] that provides dependency for [MealApplication] module
 */
@Module(subcomponents = [ MainActivitySubcomponent::class ])
abstract class  MainModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(factory: MainActivitySubcomponent.Factory): AndroidInjector.Factory<*>
}
