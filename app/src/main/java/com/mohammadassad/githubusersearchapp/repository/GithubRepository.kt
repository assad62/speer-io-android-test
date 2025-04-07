package com.mohammadassad.githubusersearchapp.repository

import com.mohammadassad.githubusersearchapp.core.Resource
import com.mohammadassad.githubusersearchapp.remoteapi.GithubApiService
import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserDetailsResponse
import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserItem
import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    private val apiService: GithubApiService
) {
    suspend fun searchUsers(
        query: String,
        page: Int = 1,
        perPage: Int = 30
    ): Resource<GithubUserResponse> {
        return try {
            Resource.Success(apiService.searchUsers(query, page, perPage))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getUserDetails(username: String): Resource<GithubUserDetailsResponse> {
        return try {
            Resource.Success(apiService.getUserDetails(username))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getFollowers(
        username: String,
        page: Int = 1,
        perPage: Int = 30
    ): Resource<List<GithubUserItem>> {
        return try {
            Resource.Success(apiService.getFollowers(username, page, perPage))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getFollowing(
        username: String,
        page: Int = 1,
        perPage: Int = 30
    ): Resource<List<GithubUserItem>> {
        return try {
            Resource.Success(apiService.getFollowing(username, page, perPage))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
} 