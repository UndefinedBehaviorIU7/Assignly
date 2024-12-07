package com.example.assignly.api.models

data class GroupsList (
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val ownerId: Int,
    val members: List<User>
)
