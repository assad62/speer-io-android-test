package com.mohammadassad.githubusersearchapp.followersscreen.domain

import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserItem
import kotlinx.coroutines.flow.Flow

interface FollowersRepository {
    fun getFollowers(username: String): Flow<List<GithubUserItem>>
} 