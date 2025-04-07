package com.mohammadassad.githubusersearchapp.followingscreen.domain

import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchFollowingUseCase @Inject constructor(
    private val repository: FollowingRepository
) {
    fun execute(username: String): Flow<List<GithubUserItem>> {
        return repository.getFollowing(username)
    }
} 