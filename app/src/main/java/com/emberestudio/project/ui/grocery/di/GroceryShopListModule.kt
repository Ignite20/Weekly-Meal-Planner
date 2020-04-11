package com.emberestudio.project.ui.grocery.di

import androidx.lifecycle.ViewModel
import com.emberestudio.project.ui.base.di.ViewModelKey
import com.emberestudio.project.ui.grocery.ui.GroceryShopListFragment
import com.emberestudio.project.ui.grocery.ui.GroceryShopListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap


@Module(
    subcomponents = [GroceryShopListSubcomponent::class]
)
abstract class GroceryShopListModule {
    @Binds
    @IntoMap
    @ClassKey(GroceryShopListFragment::class)
    internal abstract fun bindGroceryShopListFragmentInjectorFactory(factory: GroceryShopListSubcomponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ViewModelKey(GroceryShopListViewModel::class)
    abstract fun bindGroceryShopListViewModel(viewModel: GroceryShopListViewModel): ViewModel
}