package com.mohammadassad.githubusersearchapp.remoteapi

import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserDetailsResponse
import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserItem
import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): GithubUserResponse

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String
    ): GithubUserDetailsResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): List<GithubUserItem>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): List<GithubUserItem>
} 