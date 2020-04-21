package com.emberestudio.project.ui.planifications.di

import com.emberestudio.project.ui.planifications.ui.PlanificationsFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface PlanificationSubcomponent : AndroidInjector<PlanificationsFragment>{

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<PlanificationsFragment>
}