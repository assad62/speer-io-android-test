package com.mohammadassad.githubusersearchapp.remoteapi.models

import com.google.gson.annotations.SerializedName

data class GithubUserDetailsResponse(
    val login: String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val name: String?,
    val bio: String?,
    val followers: Int,
    val following: Int
) 