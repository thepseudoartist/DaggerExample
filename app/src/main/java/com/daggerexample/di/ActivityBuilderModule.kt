package com.daggerexample.di

import com.daggerexample.di.auth.AuthModule
import com.daggerexample.di.auth.AuthScope
import com.daggerexample.di.auth.AuthViewModelModule
import com.daggerexample.di.main.MainFragmentBuilderModule
import com.daggerexample.di.main.MainModule
import com.daggerexample.di.main.MainScope
import com.daggerexample.di.main.MainViewModelModule
import com.daggerexample.ui.auth.AuthActivity
import com.daggerexample.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    // Add all the activities of the project here...

    @AuthScope
    @ContributesAndroidInjector(
        modules = [
            AuthViewModelModule::class,
            AuthModule::class,
        ]
    )
    abstract fun contributeAuthActivity() : AuthActivity

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainFragmentBuilderModule::class,
            MainViewModelModule::class,
            MainModule::class,
        ]
    )
    abstract fun contributeMainActivity() : MainActivity
}