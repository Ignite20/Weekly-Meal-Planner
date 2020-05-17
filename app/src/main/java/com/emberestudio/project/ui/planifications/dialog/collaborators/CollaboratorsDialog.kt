package com.emberestudio.project.ui.planifications.dialog.collaborators

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.DialogCollaboratorsBinding
import com.emberestudio.project.ui.base.BaseDialogFragment
import com.emberestudio.project.ui.domain.model.Roles
import com.emberestudio.project.ui.managers.AuthenticationManager
import com.emberestudio.project.ui.planifications.dialog.collaborators.adapter.CollaboratorsAdapter

class CollaboratorsDialog (var callback: Actions?, var rolesList: MutableMap<String, String>?, val authenticationManager: AuthenticationManager):BaseDialogFragment(){

    interface Actions {
        fun onSaveRolesList(rolesList: MutableMap<String, String>?)
    }

    lateinit var binding: DialogCollaboratorsBinding

    lateinit var collaboratorsAdapter: CollaboratorsAdapter

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DialogCollaboratorsBinding.inflate(inflater).apply {
            lifecycleOwner = this@CollaboratorsDialog
            executePendingBindings()
        }
        prepareUI()
        return binding.root
    }

    override fun prepareUI() {
        setupCollaboratorsList()
        setupSaveButton()
        setupEditText()
        setupCloseButton()
    }

    private fun setupCollaboratorsList(){
        collaboratorsAdapter = CollaboratorsAdapter(rolesList)
        binding.rvCollaborators.apply {
            adapter = collaboratorsAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun setupEditText(){
        binding.etCollaborator.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    rolesList!![v.text.toString()] = Roles.COLLABORATOR.nName
                    binding.etCollaborator.text = Editable.Factory.getInstance().newEditable("")
                    collaboratorsAdapter.notifyDataSetChanged()
                    true
                }
                else -> false
            }
        }
    }
    private fun setupSaveButton(){
        binding.btnSaveCollaborators.setOnClickListener {
            callback?.onSaveRolesList(rolesList!!)
            dismissAllowingStateLoss()
        }
    }
    private fun setupCloseButton(){
        binding.collaboratorsCloseButton.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }
}