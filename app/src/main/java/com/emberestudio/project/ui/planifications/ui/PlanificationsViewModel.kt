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

    val _plans = MediatorLiveData<MutableList<Plan>>()
    var plans : LiveData<MutableList<Plan>> = _plans

    val _planId = MediatorLiveData<String>()
    var planId : LiveData<String> = _planId

    var selectedPlan : Plan? = null
    init {
        setupObservers()
    }

    fun getPlans(){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getPlanifications()
        }
    }

    fun deletePlan(){
        viewModelScope.launch(Dispatchers.IO) {
            selectedPlan?.let {
                useCase.removePlanification(it.id)
            }
        }
    }

    fun addNewPlan(){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.savePlanification(
                Plan(
                    planification = mutableListOf(
                        DayPlan(WeekDays.MONDAY.nName, mutableListOf()),
                        DayPlan(WeekDays.TUESDAY.nName, mutableListOf()),
                        DayPlan(WeekDays.WEDNESDAY.nName, mutableListOf()),
                        DayPlan(WeekDays.THURSDAY.nName, mutableListOf()),
                        DayPlan(WeekDays.FRIDAY.nName, mutableListOf()),
                        DayPlan(WeekDays.SATURDAY.nName, mutableListOf()),
                        DayPlan(WeekDays.SUNDAY.nName, mutableListOf())
                    ),
                    roles = mutableMapOf()
                )
            )
        }
    }

    private fun setupObservers(){
        _plans.apply {
            addSource(useCase.plansResponse.data){response ->
                createAndPostUiModel(response)
            }
        }

        _planId.apply {
            addSource(useCase.saveResponse.data){ planId ->
                postPlanID(planId)
            }
        }
    }

    fun clearObservers(){
        _planId.value = null
        _planId.removeSource(planId)
        _plans.removeSource(plans)
    }

    private fun createAndPostUiModel(list : MutableList<Plan>){
        viewModelScope.launch {
            _plans.postValue(list)
        }
    }

    private fun postPlanID(planId : String){
        viewModelScope.launch {
            _planId.postValue(planId)
        }
    }
}