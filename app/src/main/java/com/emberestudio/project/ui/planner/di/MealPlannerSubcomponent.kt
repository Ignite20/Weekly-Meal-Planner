package com.emberestudio.project.ui.planner.di

import com.emberestudio.project.ui.planner.ui.MealPlannerFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface MealPlannerSubcomponent : AndroidInjector<MealPlannerFragment> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MealPlannerFragment>
}