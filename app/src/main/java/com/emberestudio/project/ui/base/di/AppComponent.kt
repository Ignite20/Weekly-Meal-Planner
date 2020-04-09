package com.emberestudio.project.ui.base.di

import com.emberestudio.project.ui.base.MealApplication
import com.emberestudio.project.ui.domain.MealsDataSource
import com.emberestudio.project.ui.planner.di.MealPlannerModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Dagger [Component] with [MealApplication] scope.
 * Here are added [Module]s for each android module
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        MainModule::class,
        MealsDataSource::class,
        MealPlannerModule::class
    ]
)
interface AppComponent {
    fun inject(application: MealApplication)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: MealApplication): Builder
    }
}