package com.emberestudio.project.ui.meals.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.R
import com.emberestudio.project.databinding.FragmentMealsListBinding
import com.emberestudio.project.ui.base.BaseFragment
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.managers.AuthenticationManager
import com.emberestudio.project.ui.meals.adapter.MealsAdapter
import com.emberestudio.project.ui.meals.dialog.add_meal_dialog.AddMealDialog
import javax.inject.Inject

class MealsFragment : BaseFragment<MealsViewModel>(), AddMealDialog.Actions, MealsAdapter.OnItemActions {

    lateinit var binding: FragmentMealsListBinding
    lateinit var adapter: MealsAdapter
    @Inject lateinit var authManager : AuthenticationManager

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMealsListBinding.inflate(inflater).apply {
            lifecycleOwner = this@MealsFragment
            executePendingBindings()
        }
        prepareUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getMeals()
    }

    private fun prepareUI() {
        observeData()
        prepareEditButton()
        prepareRecyclerView()
    }

    private fun prepareRecyclerView() {
        adapter = MealsAdapter(mutableListOf(), null)
        binding.rvMeals.apply {
            adapter = adapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun prepareEditButton() {
        binding.fabSaveMeal.setOnClickListener {
            AddMealDialog(
                this,
                authManager = authManager
            ).show(parentFragmentManager, "")
        }
    }

    override fun onSaveMeal(item: Meal) {
        viewModel.saveMeal(item)
    }

    private fun observeData() {
        viewModel.meals.observe(this, Observer {
            binding.rvMeals.adapter = MealsAdapter(it, this)
            binding.rvMeals.adapter?.notifyDataSetChanged()
        })

    }

    override fun onItemClick(item: Meal) {
        findNavController().navigate(MealsFragmentDirections.actionMealsToMealDetail(item.id))
    }

    override fun onItemLongClick(item: Meal) {
        AddMealDialog(
            this,
            item,
            authManager = authManager
        ).show(parentFragmentManager, "")
    }

    override fun onItemDelete(position: Int) {
        showDeleteDialog(position)
    }

    private fun showDeleteDialog(position: Int){
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_meal_title)
            .setMessage(R.string.delete_meal_message)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                viewModel.removeItem(position)
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}