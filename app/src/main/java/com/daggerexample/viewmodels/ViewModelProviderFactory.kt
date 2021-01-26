package com.daggerexample.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

private const val TAG = "ViewModelProvidersFactory"

class ViewModelProviderFactory @Inject constructor(
    private val creators: MutableMap<Class<out ViewModel?>, @JvmSuppressWildcards Provider<ViewModel>>,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (creators[modelClass] == null) {
            creators[modelClass] = creators.entries.firstOrNull {
                modelClass.isAssignableFrom(it.key)
            }?.value ?: throw IllegalArgumentException("Unknown model class: $modelClass")
        }

        return try {
            @Suppress("UNCHECKED_CAST")
            creators[modelClass]?.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}