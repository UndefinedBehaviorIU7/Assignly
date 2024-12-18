package com.example.assignly.presentation.addGroup

import com.example.assignly.api.models.User

data class RequestResult (
    val code: Int,
    val result: List<User>
)
