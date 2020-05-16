package com.emberestudio.project.ui.planifications.dialog.collaborators.holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.ItemCollaboratorBinding

class CollaboratorViewHolder(var binding: ItemCollaboratorBinding) : RecyclerView.ViewHolder(binding.root){

    interface OnCollaboratorActions{
        fun onRemoved(key: String, position: Int)
    }

    var callback : OnCollaboratorActions? = null

    companion object{
        fun from(viewGroup: ViewGroup?): CollaboratorViewHolder{
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemCollaboratorBinding.inflate(layoutInflater, viewGroup, false)
            return CollaboratorViewHolder(binding)
        }
    }

    fun bind(name: String?, email: String){
        name?.let { binding.tvCollaboratorName.text = it }
        binding.tvCollaboratorEmail.text = email

        binding.ivDeleteCollaborator.setOnClickListener {
            callback?.onRemoved(email, adapterPosition)
        }
    }

    fun showDeleteButton(show : Boolean){
        binding.ivDeleteCollaborator.visibility = if(show) View.VISIBLE else View.GONE
    }
}