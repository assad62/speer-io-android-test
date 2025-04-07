package com.mohammadassad.githubusersearchapp.searchscreen.domain

import com.mohammadassad.githubusersearchapp.common.VersionControlUser
import com.mohammadassad.githubusersearchapp.core.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchUserListUseCase @Inject constructor(
    private val repository: ListUserRepository
) {
    fun execute(searchQuery: String): Flow<Resource<List<VersionControlUser>>> = flow {
        // If search query is empty, return empty array
        if (searchQuery.isEmpty()) {
            emit(Resource.Success(emptyList()))
            return@flow
        }
        
        // Delegate data fetching to the repository
        repository.fetchUserList(searchQuery).collect { resource ->
            emit(resource)
        }
    }
} 