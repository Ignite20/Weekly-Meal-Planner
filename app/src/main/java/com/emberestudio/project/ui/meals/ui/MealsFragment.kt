package com.emberestudio.project.ui.meals.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.FragmentMealsListBinding
import com.emberestudio.project.ui.base.BaseFragment
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.meals.adapter.MealsAdapter

class MealsFragment : BaseFragment<MealsViewModel>(), AddMealToPlanDialog.Actions, MealsAdapter.OnItemClick{

    lateinit var binding : FragmentMealsListBinding
    lateinit var adapter: MealsAdapter

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMealsListBinding.inflate(inflater).apply {
            lifecycleOwner = this@MealsFragment
            executePendingBindings()
        }
        prepareUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMeals()
    }
    private fun prepareUI(){
        observeData()
        prepareEditButton()
        prepareRecyclerView()
    }

    private fun prepareRecyclerView(){
        adapter = MealsAdapter(mutableListOf(), null)
        binding.rvMeals.apply {
            adapter = adapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }
    private fun prepareEditButton(){
        binding.fabSaveMeal.setOnClickListener {
            AddMealToPlanDialog(this).show(parentFragmentManager,"")
        }
    }

    override fun onSaveMeal(item: Meal, day: Int) {
        viewModel.saveMeal(item)
    }

    private fun observeData(){
        viewModel.meals.observe(this, Observer {
            binding.rvMeals.adapter = MealsAdapter(it, this)
            binding.rvMeals.adapter?.notifyDataSetChanged()
        })

    }

    override fun onItemClick(item: Meal) {
        findNavController().navigate(MealsFragmentDirections.actionMealsToMealDetail(item.id))
    }
}