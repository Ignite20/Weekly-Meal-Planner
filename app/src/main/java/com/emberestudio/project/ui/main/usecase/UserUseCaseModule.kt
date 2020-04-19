package com.emberestudio.project.ui.main.usecase

import com.emberestudio.project.ui.domain.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class UserUseCaseModule {
    @Provides
    fun provideUserUseCase(repository: Repository) : UserUseCase{
        return UserUseCaseImplementation(repository)
    }
}