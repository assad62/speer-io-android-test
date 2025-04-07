package com.mohammadassad.githubusersearchapp.di

import com.mohammadassad.githubusersearchapp.followersscreen.data.FollowersRepositoryImpl
import com.mohammadassad.githubusersearchapp.followersscreen.domain.FollowersRepository
import com.mohammadassad.githubusersearchapp.followingscreen.data.FollowingRepositoryImpl
import com.mohammadassad.githubusersearchapp.followingscreen.domain.FollowingRepository
import com.mohammadassad.githubusersearchapp.searchscreen.data.ListUserRepositoryImpl
import com.mohammadassad.githubusersearchapp.searchscreen.domain.ListUserRepository
import com.mohammadassad.githubusersearchapp.userscreen.data.UserProfileRepositoryImpl
import com.mohammadassad.githubusersearchapp.userscreen.domain.UserProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindListUserRepository(
        listUserRepositoryImpl: ListUserRepositoryImpl
    ): ListUserRepository

    @Binds
    @Singleton
    abstract fun bindUserProfileRepository(
        userProfileRepositoryImpl: UserProfileRepositoryImpl
    ): UserProfileRepository

    @Binds
    @Singleton
    abstract fun bindFollowersRepository(
        followersRepositoryImpl: FollowersRepositoryImpl
    ): FollowersRepository

    @Binds
    @Singleton
    abstract fun bindFollowingRepository(
        followingRepositoryImpl: FollowingRepositoryImpl
    ): FollowingRepository
} 