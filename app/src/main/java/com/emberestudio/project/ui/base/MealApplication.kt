package com.emberestudio.project.ui.base

import android.app.Activity
import android.app.Application
import com.emberestudio.project.ui.base.di.AppComponent
import com.emberestudio.project.ui.base.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerContentProvider
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MealApplication : Application(), HasActivityInjector {

    @Inject
    internal lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }
    override fun activityInjector() = activityInjector

}