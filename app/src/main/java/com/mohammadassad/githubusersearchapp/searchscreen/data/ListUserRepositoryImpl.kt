package com.mohammadassad.githubusersearchapp.searchscreen.data

import com.mohammadassad.githubusersearchapp.common.VersionControlUser
import com.mohammadassad.githubusersearchapp.core.Resource
import com.mohammadassad.githubusersearchapp.remoteapi.GithubApiService
import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserDetailsResponse
import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserItem
import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserResponse
import com.mohammadassad.githubusersearchapp.searchscreen.domain.ListUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListUserRepositoryImpl @Inject constructor(
    private val apiService: GithubApiService
) : ListUserRepository {

    override fun fetchUserList(username: String): Flow<Resource<List<VersionControlUser>>> = flow {
        emit(Resource.Loading())
        try {
            val searchResponse = apiService.searchUsers(query = username)
            
            if (searchResponse.items.isEmpty()) {
                emit(Resource.Success(emptyList()))
                return@flow
            }

            val users = searchResponse.items.map { item ->
                try {
                    val details = apiService.getUserDetails(username = item.login)
                    mapToDomain(details)
                } catch (e: Exception) {
                    // Fallback to basic mapping if detail fetch fails
                    VersionControlUser(
                        login = item.login,
                        name = "Unknown",
                        bio = "No bio available",
                        avatarName = item.avatarUrl,
                        followers = 0,
                        following = 0
                    )
                }
            }

            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }

    private fun mapToDomain(detailsResponse: GithubUserDetailsResponse): VersionControlUser {
        return VersionControlUser(
            login = detailsResponse.login,
            name = detailsResponse.name ?: "No name provided",
            bio = detailsResponse.bio ?: "No bio available",
            avatarName = detailsResponse.avatarUrl,
            followers = detailsResponse.followers,
            following = detailsResponse.following
        )
    }
} 