package com.mohammadassad.githubusersearchapp.remoteapi.models

import com.google.gson.annotations.SerializedName

data class GithubUserResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<GithubUserItem>
) 