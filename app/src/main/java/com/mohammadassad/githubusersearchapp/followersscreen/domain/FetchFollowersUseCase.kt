package com.mohammadassad.githubusersearchapp.followersscreen.domain

import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchFollowersUseCase @Inject constructor(
    private val repository: FollowersRepository
) {
    fun execute(username: String): Flow<List<GithubUserItem>> {
        return repository.getFollowers(username)
    }
} 