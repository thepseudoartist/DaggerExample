package com.daggerexample.di.main

import com.daggerexample.ui.main.posts.PostsFragment
import com.daggerexample.ui.main.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeProfileFragment() : ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributePostsFragment() : PostsFragment
}