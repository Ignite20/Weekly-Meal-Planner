package com.emberestudio.project.ui.profile.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emberestudio.project.databinding.FragmentProfileBinding
import com.emberestudio.project.ui.base.BaseFragment
import com.emberestudio.project.ui.managers.AuthenticationManager
import com.emberestudio.project.ui.profile.model.ProfileUiModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_profile.view.*
import javax.inject.Inject

class ProfileFragment : BaseFragment<ProfileViewModel>(), AuthenticationManager.AuthCallback{

    @Inject lateinit var authManager : AuthenticationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
    lateinit var binding : FragmentProfileBinding
    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentProfileBinding.inflate(inflater).apply {
            lifecycleOwner = this@ProfileFragment
            executePendingBindings()
        }
        binding.model = viewModel.uiData.value
        prepareUI()
        return binding.root
    }

    private fun prepareUI(){
        checkUser()
        authManager.callback = this
        binding.btnLogin.setOnClickListener {
            if(viewModel.uiData.value?.logged != null && viewModel.uiData.value?.logged!!)
                authManager.logout(this)
            else
                authManager.login(this)
        }
    }

    private fun checkUser(){
        setButtonText(viewModel.uiData.value?.logged != null)
    }

    private fun setButtonText(logged : Boolean){
        binding.btnLogin.btn_login.text = if(logged) "Logout" else "Login"
    }

    override fun onAuthSuccessful(user: FirebaseUser?) {
        user?.let {
            binding.btnLogin.btn_login.text = "Logout"
            viewModel.uiData.value = ProfileUiModel(
                user.displayName.toString(),
                user.photoUrl.toString(),
                true)
        }
        binding.model = viewModel.uiData.value

    }

    override fun onLogout() {
        binding.btnLogin.btn_login.text = "Login"
        viewModel.uiData.value = ProfileUiModel(
            "",
            "",
            false)
        binding.model = viewModel.uiData.value
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AuthenticationManager.RC_SIGN_IN) {
            if(data != null) authManager.handleSignInResult(resultCode, data)
        }
    }

    override fun onAuthFailure() {
    }

}