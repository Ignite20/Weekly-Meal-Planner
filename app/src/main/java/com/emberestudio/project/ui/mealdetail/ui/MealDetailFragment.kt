package com.emberestudio.project.ui.mealdetail.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.R
import com.emberestudio.project.databinding.FragmentMealDetailBinding
import com.emberestudio.project.ui.base.BaseFragment
import com.emberestudio.project.ui.mealdetail.adapter.MealDetailAdapter

private const val TAB_INGREDIENTS = "ingredients"
private const val TAB_STEPS = "steps"

class MealDetailFragment : BaseFragment<MealDetailViewModel>() {

    private val args : MealDetailFragmentArgs by navArgs()
    lateinit var binding : FragmentMealDetailBinding

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMealDetailBinding.inflate(inflater).apply {
            lifecycleOwner
            executePendingBindings()
        }
        prepareUI()
        return binding.root
    }

    private fun retrieveArgs(){
        args.let {
            viewModel.getMeal(it.meal)
        }
    }

    private fun prepareUI(){
        observeData()
        retrieveArgs()

    }

    private fun prepareDetails(){
        binding.tabHost.setup()
        prepareIngredients()
        prepareSteps()
    }
    private fun prepareIngredientsRV(){
        binding.rvDetailsIngredients.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = viewModel.uiData.value?.ingredients?.let { MealDetailAdapter(it) }
        }
    }

    private fun prepareIngredients(){
        val tabSpec = binding.tabHost.newTabSpec(TAB_INGREDIENTS)
        tabSpec.setContent(R.id.tab_ingredients)
        tabSpec.setIndicator(getString(R.string.meal_ingredients_title))
        binding.tabHost.addTab(tabSpec)
        prepareIngredientsRV()
    }

    private fun prepareStepsRV(){
        binding.rvDetailsSteps.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = viewModel.uiData.value?.steps?.let { MealDetailAdapter(it) }
        }
    }
    private fun prepareSteps(){
        val tabSpec = binding.tabHost.newTabSpec(TAB_STEPS)
        tabSpec.setContent(R.id.tab_steps)
        tabSpec.setIndicator(getString(R.string.meal_steps_title))
        binding.tabHost.addTab(tabSpec)
        prepareStepsRV()
    }
    private fun observeData(){
        viewModel.mealItem.observe(viewLifecycleOwner, Observer {
            binding.model = it
            prepareDetails()
        })
    }
}