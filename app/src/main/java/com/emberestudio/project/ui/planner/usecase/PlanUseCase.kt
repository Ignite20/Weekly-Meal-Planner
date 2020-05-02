package com.emberestudio.project.ui.planner.usecase

import com.emberestudio.project.ui.domain.model.Plan
import com.emberestudio.project.ui.domain.repository.Repository
import com.emberestudio.project.ui.domain.usecase.base.PlanUseCaseContract
import com.emberestudio.project.ui.domain.usecase.base.SingleUseCaseImplementation
import javax.inject.Inject

interface PlanUseCase : PlanUseCaseContract

class PlanUseCaseImplementation @Inject constructor(private val repository: Repository) : PlanUseCase{
    override val planResponse = SingleUseCaseImplementation<Plan>()
    override val saveResponse = SingleUseCaseImplementation<String>()

    override fun getPlan(planId : String) {
        repository.getPlan(planResponse, planId)
    }

    override fun savePlan(plan: Plan) {
        repository.savePlan(saveResponse, plan)
    }
}