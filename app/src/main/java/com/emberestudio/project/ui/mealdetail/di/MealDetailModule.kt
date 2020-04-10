package com.emberestudio.project.ui.mealdetail.di

import androidx.lifecycle.ViewModel
import com.emberestudio.project.ui.base.di.ViewModelKey
import com.emberestudio.project.ui.mealdetail.ui.MealDetailFragment
import com.emberestudio.project.ui.mealdetail.ui.MealDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [MealDetailSubcomponent::class]
)
abstract class MealDetailModule {

    @Binds
    @IntoMap
    @ClassKey(MealDetailFragment::class)
    internal abstract fun bindMealDetailFragmentInjectorFactory(factory: MealDetailSubcomponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ViewModelKey(MealDetailViewModel::class)
    abstract fun bindMealDetailViewModel(viewModel: MealDetailViewModel): ViewModel
}