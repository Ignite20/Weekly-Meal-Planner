package com.emberestudio.project.ui.planner.di

import com.emberestudio.project.ui.domain.MealsDataSource
import com.emberestudio.project.ui.domain.repository.Repository
import com.emberestudio.project.ui.planner.usecase.MealUseCase
import com.emberestudio.project.ui.planner.usecase.MealUseCaseImplementation
import dagger.Module
import dagger.Provides

@Module(includes = [MealsDataSource::class])
class MealUseCaseModule {
    @Provides
    fun provideMealUseCase(repository: Repository) : MealUseCase{
        return MealUseCaseImplementation(repository)
    }
}