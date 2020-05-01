package com.emberestudio.project.ui.planner.di

import androidx.lifecycle.ViewModel
import com.emberestudio.project.ui.base.di.ViewModelKey
import com.emberestudio.project.ui.planner.ui.PlanFragment
import com.emberestudio.project.ui.planner.ui.PlanViewModel
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [PlanSubcomponent::class],
    includes = [PlanUseCaseModule::class]
)
abstract class PlanModule {

    @Binds
    @IntoMap
    @ClassKey(PlanFragment::class)
    internal abstract fun bindMealPlannerFragmentInjectorFactory(factory: PlanSubcomponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ViewModelKey(PlanViewModel::class)
    abstract fun bindMealPlannerViewModel(viewModel: PlanViewModel): ViewModel
}