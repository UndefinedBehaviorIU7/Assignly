package com.example.assignly.presentation.addGroup

import android.net.Uri
import com.example.assignly.api.NetworkService
import com.example.assignly.api.models.User

sealed class AddGroupUiState {
    data class Idle (
        val name: String = "",
        val description: String = "",
        val members: List<User> = emptyList<User>(),
        val allUsers: List<User> = emptyList<User>(),
        val image: Uri? = null
    ): AddGroupUiState()

    data class Loading (
        val name: String,
        val description: String,
        val members: List<User>,
        val allUsers: List<User>,
        val image: Uri?
    ): AddGroupUiState()

    data class Error (
        val name: String,
        val description: String,
        val members: List<User>,
        val allUsers: List<User>,
        val image: Uri?,
        val errorMessage: String
    ): AddGroupUiState()

    data class Success (
        val successMessage: String
    ): AddGroupUiState()
}