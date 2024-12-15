package com.example.assignly.api.models

data class Task (
    val id: Int,
    val groupId: Int,
    val ownerId: Int,
    val name: String,
    val summary: String,
    val description: String,
    val deadline: String,
    val status: Int,
    val members: List<User>
)
