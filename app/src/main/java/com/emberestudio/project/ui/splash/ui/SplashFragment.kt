package com.emberestudio.project.ui.splash.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.emberestudio.project.databinding.SplashViewBinding
import com.emberestudio.project.ui.base.BaseFragment

class SplashFragment : BaseFragment<SplashViewModel>() {

    lateinit var binding : SplashViewBinding

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = SplashViewBinding.inflate(inflater).apply {
            lifecycleOwner = this@SplashFragment
            executePendingBindings()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler().postDelayed({
            toMainScreen()
        }, 2000)
    }

    private fun toMainScreen(){
        findNavController().navigate(SplashFragmentDirections.actionSplashToPlanner())
    }

}