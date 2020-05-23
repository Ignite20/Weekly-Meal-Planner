package com.emberestudio.project.ui.grocery.ui

import com.emberestudio.project.ui.base.BaseViewModel
import com.emberestudio.project.ui.domain.model.Grocery
import javax.inject.Inject

class GroceryShopListViewModel @Inject constructor(): BaseViewModel() {

    var grocery : Grocery = Grocery()
}