package com.emberestudio.project.ui.main.usecase

import com.emberestudio.project.ui.domain.repository.Repository
import com.emberestudio.project.ui.domain.usecase.base.SingleUseCaseImplementation
import com.emberestudio.project.ui.domain.usecase.base.UserUseCaseContract
import javax.inject.Inject

interface UserUseCase : UserUseCaseContract

class UserUseCaseImplementation @Inject constructor(private val repository: Repository) : UserUseCase{
    override val userResponse = SingleUseCaseImplementation<Boolean>()

    override fun getCurrentUser() {
        repository.getCurrentUser(userResponse)
    }
}