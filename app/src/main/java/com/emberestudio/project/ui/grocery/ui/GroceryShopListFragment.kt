package com.emberestudio.project.ui.grocery.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.FragmentGroceryListBinding
import com.emberestudio.project.ui.base.BaseFragment
import com.emberestudio.project.ui.domain.model.GroceryItem
import com.emberestudio.project.ui.grocery.adapter.GroceryAdapter

class GroceryShopListFragment : BaseFragment<GroceryShopListViewModel>(), GroceryAdapter.GroceryAdapterActions {

    lateinit var binding : FragmentGroceryListBinding

    lateinit var groceryAdapter : GroceryAdapter

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentGroceryListBinding.inflate(inflater).apply {
            lifecycleOwner = this@GroceryShopListFragment
            executePendingBindings()
        }
        prepareUi()
        return binding.root
    }

    private fun prepareUi(){
        prepareAdapter()
        prepareAddGroceryItem()
    }

    private fun prepareAdapter(){
        groceryAdapter = GroceryAdapter(viewModel.grocery.list!!, this)
        binding.rvGroceryList.apply{
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = groceryAdapter
        }
    }

    private fun prepareAddGroceryItem(){
        binding.addGroceryItem.setOnClickListener {
            viewModel.grocery.list?.let {
                addItemToShopList(it.lastIndex.plus(1))
            }

        }
    }

    override fun onAddGroceryItem(position: Int) {
        addItemToShopList(position)
    }

    private fun addItemToShopList(position : Int, item: GroceryItem = GroceryItem()){
        groceryAdapter.focusPosition = position
        viewModel.grocery.list?.add(position, item)
        groceryAdapter.notifyItemInserted(position)
        binding.rvGroceryList.smoothScrollToPosition(position)
    }

    private fun addItemAtTheEndToShopList(item: GroceryItem){
        viewModel.grocery.list?.add(item)
    }

    override fun onDeleteGroceryItem(position: Int) {
        viewModel.grocery.list?.let {
            it.removeAt(position)
            groceryAdapter.notifyItemRemoved(position)
        }
    }

    override fun onCheckedGroceryItem(isChecked: Boolean, position: Int) {
        viewModel.grocery.list?.let {
            it[position].checked = isChecked
//            groceryAdapter.notifyItemChanged(position)
        }
    }
}