package com.emberestudio.project.ui.meals.di

import androidx.lifecycle.ViewModel
import com.emberestudio.project.ui.base.di.ViewModelKey
import com.emberestudio.project.ui.meals.ui.MealsFragment
import com.emberestudio.project.ui.meals.ui.MealsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [MealsSubcomponent::class]
)
abstract class MealsModule {
    @Binds
    @IntoMap
    @ClassKey(MealsFragment::class)
    internal abstract fun bindMealsFragmentInjectorFactory(factory: MealsSubcomponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ViewModelKey(MealsViewModel::class)
    abstract fun bindMealsViewModel(viewModel: MealsViewModel): ViewModel
}