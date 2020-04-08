package com.emberestudio.project.ui.planner.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emberestudio.project.ui.base.ViewModelFactory
import com.emberestudio.project.ui.base.di.ViewModelKey
import com.emberestudio.project.ui.planner.ui.MealPlannerFragment
import com.emberestudio.project.ui.planner.ui.MealPlannerViewModel
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [MealPlannerSubcomponent::class])
abstract class MealPlannerModule {

    @Binds
    @IntoMap
    @ClassKey(MealPlannerFragment::class)
    internal abstract fun bindMealPlannerFragmentInjectorFactory(factory: MealPlannerSubcomponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ViewModelKey(MealPlannerViewModel::class)
    abstract fun bindMealPlannerViewModel(viewModel: MealPlannerViewModel): ViewModel
}