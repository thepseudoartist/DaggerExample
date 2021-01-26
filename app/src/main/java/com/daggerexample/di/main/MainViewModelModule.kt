package com.daggerexample.di.main

import androidx.lifecycle.ViewModel
import com.daggerexample.di.ViewModelKey
import com.daggerexample.ui.main.posts.PostsViewModel
import com.daggerexample.ui.main.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    abstract fun bindPostsViewModel(viewModel: PostsViewModel) : ViewModel
}