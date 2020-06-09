package com.example.mentions.business

data class UserDto(
    val id: Long,
    val name: String,
    val pictureUrl: String?,
    val userName: String // Backend
)