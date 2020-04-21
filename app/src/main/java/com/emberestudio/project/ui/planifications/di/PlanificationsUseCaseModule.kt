package com.emberestudio.project.ui.planifications.di

import com.emberestudio.project.ui.domain.repository.Repository
import com.emberestudio.project.ui.planifications.usecase.PlanificationsUseCase
import com.emberestudio.project.ui.planifications.usecase.PlanificationsUseCaseImplementation
import dagger.Module
import dagger.Provides

@Module
class PlanificationsUseCaseModule {
    @Provides
    fun providesPlanificationsUseCase(repository: Repository) : PlanificationsUseCase{
        return PlanificationsUseCaseImplementation(repository)
    }
}