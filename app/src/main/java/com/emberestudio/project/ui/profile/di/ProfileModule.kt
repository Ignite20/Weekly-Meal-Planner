package com.emberestudio.project.ui.profile.di

import androidx.lifecycle.ViewModel
import com.emberestudio.project.ui.base.di.ViewModelKey
import com.emberestudio.project.ui.profile.ui.ProfileFragment
import com.emberestudio.project.ui.profile.ui.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [ProfileSubcomponent::class]
)
abstract class ProfileModule {
    @Binds
    @IntoMap
    @ClassKey(ProfileFragment::class)
    internal abstract fun bindProfileFragmentInjectorFactory(factory: ProfileSubcomponent.Factory): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel
}