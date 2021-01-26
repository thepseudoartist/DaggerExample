package com.daggerexample.di.auth

import androidx.lifecycle.ViewModel
import com.daggerexample.di.ViewModelKey
import com.daggerexample.ui.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel) : ViewModel
}