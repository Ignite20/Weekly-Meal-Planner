package com.emberestudio.project.ui.grocery.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emberestudio.project.databinding.FragmentGroceryListBinding
import com.emberestudio.project.ui.base.BaseFragment

class GroceryShopListFragment : BaseFragment<GroceryShopListViewModel>() {

    lateinit var binding : FragmentGroceryListBinding
    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentGroceryListBinding.inflate(inflater).apply {
            lifecycleOwner = this@GroceryShopListFragment
            executePendingBindings()
        }

        return binding.root
    }
}