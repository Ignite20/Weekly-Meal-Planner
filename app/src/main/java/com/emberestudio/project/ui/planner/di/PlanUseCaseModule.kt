package com.emberestudio.project.ui.planner.di

import com.emberestudio.project.ui.domain.datasource.firebase.FireBaseDataSourceImpl
import com.emberestudio.project.ui.domain.datasource.local.MealsDataSource
import com.emberestudio.project.ui.domain.repository.Repository
import com.emberestudio.project.ui.planner.usecase.MealUseCase
import com.emberestudio.project.ui.planner.usecase.MealUseCaseImplementation
import com.emberestudio.project.ui.planner.usecase.PlanUseCase
import com.emberestudio.project.ui.planner.usecase.PlanUseCaseImplementation
import dagger.Module
import dagger.Provides

@Module(includes = [MealsDataSource::class, FireBaseDataSourceImpl::class])
class PlanUseCaseModule {
    @Provides
    fun provideMealUseCase(repository: Repository) : MealUseCase{
        return MealUseCaseImplementation(repository)
    }

    @Provides
    fun providePlanUseCase(repository: Repository) : PlanUseCase{
        return PlanUseCaseImplementation(repository)
    }
}