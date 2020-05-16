package com.emberestudio.project.ui.planifications.dialog.collaborators.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.ui.domain.model.Roles
import com.emberestudio.project.ui.planifications.dialog.collaborators.holders.CollaboratorViewHolder

class CollaboratorsAdapter(var rolesList: MutableMap<String, String>?) : RecyclerView.Adapter<CollaboratorViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollaboratorViewHolder {
        return CollaboratorViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return if(rolesList != null)
            rolesList!!.size
        else
            0
    }

    override fun onBindViewHolder(holder: CollaboratorViewHolder, position: Int) {

        if(rolesList != null) {
            holder.bind("", rolesList!!.keys.elementAt(position))
            holder.showDeleteButton(
                rolesList!!.values.elementAt(position).equals(Roles.COLLABORATOR.nName, true)
            )
            holder.callback = object : CollaboratorViewHolder.OnCollaboratorActions {
                override fun onRemoved(key: String, position: Int) {
                    rolesList!!.remove(key)
                    notifyDataSetChanged()
                }
            }
        }
    }
}