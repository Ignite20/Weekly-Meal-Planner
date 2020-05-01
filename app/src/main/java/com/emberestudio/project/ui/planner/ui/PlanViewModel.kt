package com.emberestudio.project.ui.planner.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.emberestudio.project.ui.base.BaseViewModel
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.Plan
import com.emberestudio.project.ui.planner.usecase.MealUseCase
import com.emberestudio.project.ui.planner.usecase.PlanUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlanViewModel @Inject constructor(private val mealUseCase: MealUseCase, private val planUseCase: PlanUseCase) : BaseViewModel() {

    val uiData = MediatorLiveData<Plan>()
    var plan : LiveData<Plan> = uiData

    val change = MediatorLiveData<Boolean>()
    var _change : LiveData<Boolean> = change
    var groupState : ArrayList<Int> = arrayListOf()

    init {
        setupObservers()
    }

    fun getPlan(planId : String) {
        viewModelScope.launch(Dispatchers.IO) {
            planUseCase.getPlan(planId)
        }
    }

    fun saveMeal(day: Int, item : Meal){
        mealUseCase.saveMeal(day, item)
    }

    fun updateOrder(from: IntArray?, to: IntArray?){
        mealUseCase.updateMeal(from!!, to!!)
    }

    private fun setupObservers(){
        uiData.apply {
            addSource(planUseCase.planResponse.data){ response ->
                createAndPostUiModel(response)
            }
        }

        change.apply {
            addSource(planUseCase.saveResponse.data){ response ->
                postChangeSuccessful(response)
            }
        }
    }

    private fun createAndPostUiModel(response: Plan) {
        viewModelScope.launch {
            uiData.postValue(response)
        }
    }

    private fun postChangeSuccessful(response : Boolean){
        viewModelScope.launch {
            change.postValue(response)
        }
    }

    fun setExpansibleState(expanded : Int){
        groupState.add(expanded)
    }

    fun removeExpansibleState(expanded: Int){
        groupState.remove(expanded)
    }
}