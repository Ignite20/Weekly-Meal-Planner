package com.emberestudio.project.ui.planner.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.emberestudio.project.ui.base.BaseViewModel
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.MealSnapshot
import com.emberestudio.project.ui.domain.model.Plan
import com.emberestudio.project.ui.planner.usecase.MealUseCase
import com.emberestudio.project.ui.planner.usecase.PlanUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlanViewModel @Inject constructor(private val mealUseCase: MealUseCase, private val planUseCase: PlanUseCase) : BaseViewModel() {

    val _plan = MediatorLiveData<Plan>()
    var plan : LiveData<Plan> = _plan

    val _meals = MediatorLiveData<MutableList<Meal>>()
    var meals : LiveData<MutableList<Meal>> = _meals

    val change = MediatorLiveData<String>()
    var _change : LiveData<String> = change
    var groupState : ArrayList<Int> = arrayListOf()

    var dayPosition = -1

    init {
        setupObservers()
    }

    fun getPlan(planId : String) {
        viewModelScope.launch(Dispatchers.IO) {
            planUseCase.getPlan(planId)
        }
    }

    fun updateTitle(planTitle : String){
        val plan = plan.value
        plan?.let {
            it.title = planTitle
            planUseCase.savePlan(it)
        }

    }

    fun updatePlanfication(){
        val plan = plan.value
        plan?.let {
            planUseCase.savePlan(it)
        }
    }

    fun updatePlanfication(mealSnapshot: MealSnapshot){
        if(dayPosition != -1) {
            val plan = plan.value
            plan?.let {
                it.planification[dayPosition].meals.add(mealSnapshot)
                planUseCase.savePlan(it)
            }
        }
        dayPosition = -1
    }

    fun getMeals(selectedDayPosition : Int){
        this.dayPosition = selectedDayPosition
        mealUseCase.getMeals()
    }

    private fun setupObservers(){
        _plan.apply {
            addSource(planUseCase.planResponse.data){ response ->
                createAndPostUiModel(response)
            }
        }

        change.apply {
            addSource(planUseCase.saveResponse.data){ response ->
                postChangeSuccessful(response)
            }
        }

        _meals.apply {
            addSource(mealUseCase.mealsResponse.data){ response ->
                postMealsList(response)
            }
        }

    }

    private fun createAndPostUiModel(response: Plan) {
        viewModelScope.launch {
            _plan.postValue(response)
        }
    }

    private fun postChangeSuccessful(response : String){
        viewModelScope.launch {
            change.postValue(response)
        }
    }
    private fun postMealsList(response: MutableList<Meal>){
        viewModelScope.launch {
            _meals.postValue(response)
        }
    }
}