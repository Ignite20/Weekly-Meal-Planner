package com.emberestudio.project.ui.base.di

import com.emberestudio.project.ui.base.MealApplication
import com.emberestudio.project.ui.domain.datasource.firebase.FireBaseDataSourceImpl
import com.emberestudio.project.ui.grocery.di.GroceryShopListModule
import com.emberestudio.project.ui.managers.AuthenticationManager
import com.emberestudio.project.ui.managers.FireCloudManager
import com.emberestudio.project.ui.mealdetail.di.MealDetailModule
import com.emberestudio.project.ui.meals.di.MealsModule
import com.emberestudio.project.ui.planifications.di.PlanificationModule
import com.emberestudio.project.ui.planner.di.PlanModule
import com.emberestudio.project.ui.profile.di.ProfileModule
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
        FireBaseDataSourceImpl::class,
        AuthenticationManager::class,
        FireCloudManager::class,
        PlanificationModule::class,
        PlanModule::class,
        MealDetailModule::class,
        GroceryShopListModule::class,
        MealsModule::class,
        ProfileModule::class
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