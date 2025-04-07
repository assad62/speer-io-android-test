package com.mohammadassad.githubusersearchapp.userscreen.domain

import com.mohammadassad.githubusersearchapp.common.VersionControlUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    fun execute(username: String): Flow<VersionControlUser?> = flow {
        if (username.isEmpty()) {
            emit(null)
            return@flow
        }
        
        repository.fetchUser(username).collect { user ->
            emit(user)
        }
    }
} 