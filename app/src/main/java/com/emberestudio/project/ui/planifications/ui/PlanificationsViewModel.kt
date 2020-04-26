package com.emberestudio.project.ui.planifications.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.emberestudio.project.ui.base.BaseViewModel
import com.emberestudio.project.ui.domain.model.DayPlan
import com.emberestudio.project.ui.domain.model.Plan
import com.emberestudio.project.ui.domain.model.WeekDays
import com.emberestudio.project.ui.planifications.usecase.PlanificationsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlanificationsViewModel @Inject constructor(val useCase : PlanificationsUseCase) : BaseViewModel(){

    val uiData = MediatorLiveData<MutableList<Plan>>()
    var plans : LiveData<MutableList<Plan>> = uiData

    init {
        setupObservers()
    }

    fun getPlans(){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getPlanifications()
        }
    }

    fun savePlan(plan: Plan?){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.savePlanificaction(
                Plan(
                    title = "Test",
                    planification = mutableListOf(DayPlan(WeekDays.MONDAY.nName, mutableListOf())),
                    roles = mutableMapOf()
                )
            )
        }
    }

    private fun setupObservers(){
        uiData.apply {
            addSource(useCase.plansResponse.data){response ->
                createAndPostUiModel(response)
            }
        }
    }

    private fun createAndPostUiModel(list : MutableList<Plan>){
        viewModelScope.launch {
            uiData.postValue(list)
        }
    }
}