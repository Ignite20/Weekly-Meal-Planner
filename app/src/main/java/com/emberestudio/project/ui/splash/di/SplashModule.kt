package com.emberestudio.project.ui.splash.di

import androidx.lifecycle.ViewModel
import com.emberestudio.project.ui.base.di.ViewModelKey
import com.emberestudio.project.ui.splash.ui.SplashFragment
import com.emberestudio.project.ui.splash.ui.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [SplashSubcomponent::class]
)
abstract class SplashModule {

    @Binds
    @IntoMap
    @ClassKey(SplashFragment::class)
    internal abstract fun bindSplashFragmentInjectorFactory(factory: SplashSubcomponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel
}