package com.mohammadassad.githubusersearchapp.followersscreen.data

import com.mohammadassad.githubusersearchapp.followersscreen.domain.FollowersRepository
import com.mohammadassad.githubusersearchapp.remoteapi.GithubApiService
import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FollowersRepositoryImpl @Inject constructor(
    private val apiService: GithubApiService
) : FollowersRepository {

    override fun getFollowers(username: String): Flow<List<GithubUserItem>> = flow {
        try {
            val followers = apiService.getFollowers(username)
            emit(followers)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
} 