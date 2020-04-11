package com.emberestudio.project.ui.grocery.di

import com.emberestudio.project.ui.grocery.ui.GroceryShopListFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface GroceryShopListSubcomponent : AndroidInjector<GroceryShopListFragment>{
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<GroceryShopListFragment>
}