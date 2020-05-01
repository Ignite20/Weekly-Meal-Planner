package com.emberestudio.project.ui.planner.di

import com.emberestudio.project.ui.planner.ui.PlanFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface PlanSubcomponent : AndroidInjector<PlanFragment> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<PlanFragment>
}