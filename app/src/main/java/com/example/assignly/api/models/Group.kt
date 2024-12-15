package com.example.assignly.api.models

data class Group (
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val ownerId: Int,
    val members: List<User>
)
