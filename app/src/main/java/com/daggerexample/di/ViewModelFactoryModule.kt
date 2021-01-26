package com.daggerexample.di

import androidx.lifecycle.ViewModelProvider
import com.daggerexample.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    /*
     * @Provides
     * fun bindFactory(factory: ViewModelProviderFactory) : ViewModelProvider.Factory {
     *      return factory
     * }
     *
     * Instead of writing above piece of code we use a more efficient way to provide dependency.
    */

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}