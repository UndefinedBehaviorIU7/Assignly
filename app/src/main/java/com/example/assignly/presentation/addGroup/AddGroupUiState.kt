package com.example.assignly.presentation.addGroup

import android.net.Uri
import com.example.assignly.api.NetworkService
import com.example.assignly.api.models.User

sealed class AddGroupUiState {
    data class Idle (
        val name: String = "",
        val description: String = "",
        val members: MutableList<User> = mutableListOf<User>(),
        val allUsers: List<User> = emptyList<User>(),
        val image: Uri? = null,
        var menuExpanded: Boolean = false
    ): AddGroupUiState()

    data class Loading (
        val name: String,
        val description: String,
        val members: MutableList<User>,
        val allUsers: List<User>,
        val image: Uri?,
        var menuExpanded: Boolean
    ): AddGroupUiState()

    data class Error (
        val name: String,
        val description: String,
        val members: MutableList<User>,
        val allUsers: List<User>,
        val image: Uri?,
        val errorMessage: String,
        var menuExpanded: Boolean
    ): AddGroupUiState()

    data class Success (
        val successMessage: String
    ): AddGroupUiState()
}