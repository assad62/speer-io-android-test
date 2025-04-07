package com.mohammadassad.githubusersearchapp.searchscreen.domain

import com.mohammadassad.githubusersearchapp.common.VersionControlUser
import com.mohammadassad.githubusersearchapp.core.Resource
import kotlinx.coroutines.flow.Flow

interface ListUserRepository {
    fun fetchUserList(username: String): Flow<Resource<List<VersionControlUser>>>
} 