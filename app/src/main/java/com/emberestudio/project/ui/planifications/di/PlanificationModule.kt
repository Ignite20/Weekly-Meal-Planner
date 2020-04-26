package com.emberestudio.project.ui.planifications.di

import androidx.lifecycle.ViewModel
import com.emberestudio.project.ui.base.di.ViewModelKey
import com.emberestudio.project.ui.planifications.ui.PlanificationsFragment
import com.emberestudio.project.ui.planifications.ui.PlanificationsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [PlanificationSubcomponent::class],
    includes = [PlanificationsUseCaseModule::class]
)
abstract class PlanificationModule {

    @Binds
    @IntoMap
    @ClassKey(PlanificationsFragment::class)
    internal abstract fun bindPlanificationsFragmentInjectorFactory(factory: PlanificationSubcomponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ViewModelKey(PlanificationsViewModel::class)
    abstract fun bindPlanificationsViewModel(viewModel: PlanificationsViewModel): ViewModel
}