package com.mohammadassad.githubusersearchapp.followingscreen.domain

import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserItem
import kotlinx.coroutines.flow.Flow

interface FollowingRepository {
    fun getFollowing(username: String): Flow<List<GithubUserItem>>
} 