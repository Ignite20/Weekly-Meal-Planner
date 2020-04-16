package com.emberestudio.project.ui.mealdetail.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.ItemDetailBinding

class ItemDetailViewHolder(var binding : ItemDetailBinding) : RecyclerView.ViewHolder(binding.root){

    companion object {
        fun from(viewGroup: ViewGroup?): ItemDetailViewHolder {
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemDetailBinding.inflate(layoutInflater, viewGroup, false)
            return ItemDetailViewHolder(binding)
        }
    }

    fun bind(string1 : String, string2 : String){
        binding.string1.text = string1
        binding.string2.text = string2
    }
}