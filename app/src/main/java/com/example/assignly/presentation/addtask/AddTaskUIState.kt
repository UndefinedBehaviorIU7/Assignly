package com.example.assignly.presentation.addtask

import android.net.Uri
import com.example.assignly.api.models.User
import com.example.assignly.presentation.signup.SignupUiState
import retrofit2.http.Query

sealed class AddTaskUIState {
    data class Idle (
        val groupId: Int = 0,
        val ownerId: Int = 0,
        val name: String = "",
        val description: String = "",
        val summary: String = "",
        val deadline: String = "",
        val status: Int = 0,
        val members: List<User> = mutableListOf(),
    ): AddTaskUIState()

    data class Error (
        val groupId: Int,
        val ownerId: Int,
        val name: String,
        val description: String,
        val summary: String,
        val deadline: String,
        val status: Int,
        val members: List<User>
    ): AddTaskUIState()

    data class Loading (
        val groupId: Int,
        val ownerId: Int,
        val name: String,
        val description: String,
        val summary: String,
        val deadline: String,
        val status: Int,
        val members: List<User>
    ): AddTaskUIState()

}
