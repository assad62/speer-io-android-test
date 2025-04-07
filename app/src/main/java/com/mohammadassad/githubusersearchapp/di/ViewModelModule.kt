package com.mohammadassad.githubusersearchapp.di

import androidx.lifecycle.SavedStateHandle
import com.mohammadassad.githubusersearchapp.userscreen.presentation.UserProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideUsername(savedStateHandle: SavedStateHandle): String {
        return savedStateHandle.get<String>("username") ?: throw IllegalStateException("Username is required")
    }
} 