package com.emberestudio.project.ui.planner.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.emberestudio.project.ui.base.BaseViewModel
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.planner.usecase.MealUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealPlannerViewModel @Inject constructor(private val mealUseCase: MealUseCase) : BaseViewModel() {

    val uiData = MediatorLiveData<MutableMap<Int, MutableList<Meal>>>()
    var plan : LiveData<MutableMap<Int, MutableList<Meal>>> = uiData
    val change = MediatorLiveData<Boolean>()
    var _change : LiveData<Boolean> = change
    var groupState : ArrayList<Int> = arrayListOf()

    init {
        setupObservers()
    }

    fun getMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            mealUseCase.getPlan()
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
            addSource(mealUseCase.planResponse.data){ response ->
                createAndPostUiModel(response)
            }
        }

        change.apply {
            addSource(mealUseCase.changeResponse.data){ response ->
                postChangeSuccessful(response)
            }
        }
    }

    private fun createAndPostUiModel(response: MutableMap<Int, MutableList<Meal>>) {
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