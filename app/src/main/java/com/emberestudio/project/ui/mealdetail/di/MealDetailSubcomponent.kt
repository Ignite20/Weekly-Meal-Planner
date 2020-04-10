package com.emberestudio.project.ui.mealdetail.di

import com.emberestudio.project.ui.mealdetail.ui.MealDetailFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface MealDetailSubcomponent : AndroidInjector<MealDetailFragment>{

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MealDetailFragment>
}