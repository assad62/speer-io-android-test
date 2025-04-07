package com.mohammadassad.githubusersearchapp.userscreen.data

import android.util.Log
import com.mohammadassad.githubusersearchapp.common.VersionControlUser
import com.mohammadassad.githubusersearchapp.remoteapi.GithubApiService
import com.mohammadassad.githubusersearchapp.userscreen.domain.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val apiService: GithubApiService
) : UserProfileRepository {

    override fun fetchUser(username: String): Flow<VersionControlUser?> = flow {
        try {
            Log.d("UserProfileRepository", "Fetching user profile for username: $username")
            val response = apiService.getUserDetails(username)
            Log.d("UserProfileRepository", "API response received: $response")
            val user = mapToDomain(response)
            Log.d("UserProfileRepository", "Mapped to domain model: $user")
            emit(user)
        } catch (e: Exception) {
            Log.e("UserProfileRepository", "Error fetching user profile", e)
            emit(null)
        }
    }

    private fun mapToDomain(response: com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserDetailsResponse): VersionControlUser {
        return VersionControlUser(
            login = response.login,
            name = response.name ?: "No name provided",
            bio = response.bio ?: "No bio available",
            avatarName = response.avatarUrl,
            followers = response.followers,
            following = response.following
        )
    }
} 