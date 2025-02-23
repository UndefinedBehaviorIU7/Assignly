package com.example.assignly.presentation.infoGroup

import com.example.assignly.api.models.User

sealed class infoGroupUIState {
    data class Idle(
        val groupId: Int = 21,
        val name: String = "q",
        val description: String = "",
        val members: List<User> = emptyList<User>(),
        val image: String = ""
    ): infoGroupUIState()
}