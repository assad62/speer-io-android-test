package com.mohammadassad.githubusersearchapp.common

import java.util.UUID

data class VersionControlUser(
    val login: String,
    val name: String,
    val bio: String,
    val avatarName: String,
    val followers: Int,
    val following: Int
) {
    val id: UUID = UUID.randomUUID()
}
