package com.mohammadassad.githubusersearchapp.userscreen.domain

import com.mohammadassad.githubusersearchapp.common.VersionControlUser
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun fetchUser(username: String): Flow<VersionControlUser?>
} 