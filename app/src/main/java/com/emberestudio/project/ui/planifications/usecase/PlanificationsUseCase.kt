package com.emberestudio.project.ui.planifications.usecase

import com.emberestudio.project.ui.domain.model.Plan
import com.emberestudio.project.ui.domain.repository.Repository
import com.emberestudio.project.ui.domain.usecase.base.PlanificationsUseCaseContract
import com.emberestudio.project.ui.domain.usecase.base.SingleUseCaseImplementation
import javax.inject.Inject

interface PlanificationsUseCase : PlanificationsUseCaseContract

class PlanificationsUseCaseImplementation @Inject constructor(private val repository: Repository): PlanificationsUseCase{
    override val plansResponse = SingleUseCaseImplementation<MutableList<Plan>>()

    override fun getPlanifications() {
        repository.getPlanifications(plansResponse)
    }

    override fun savePlanificaction(plan: Plan) {
        repository.savePlanification(plan, plansResponse)
    }

}