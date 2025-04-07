package com.mohammadassad.githubusersearchapp.followingscreen.data

import com.mohammadassad.githubusersearchapp.followingscreen.domain.FollowingRepository
import com.mohammadassad.githubusersearchapp.remoteapi.GithubApiService
import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FollowingRepositoryImpl @Inject constructor(
    private val apiService: GithubApiService
) : FollowingRepository {

    override fun getFollowing(username: String): Flow<List<GithubUserItem>> = flow {
        try {
            val following = apiService.getFollowing(username)
            emit(following)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
} 