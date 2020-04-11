package com.emberestudio.project.ui.meals.di

import com.emberestudio.project.ui.meals.ui.MealsFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface MealsSubcomponent : AndroidInjector<MealsFragment>{
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MealsFragment>
}