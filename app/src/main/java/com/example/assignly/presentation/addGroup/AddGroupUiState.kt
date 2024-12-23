package com.example.assignly.presentation.addGroup

import android.net.Uri
import androidx.compose.ui.geometry.Size
import com.example.assignly.api.models.User

sealed class AddGroupUiState {
    data class Idle (
        val name: String = "",
        val description: String = "",
        val members: MutableList<User> = mutableListOf<User>(),
        val allUsers: List<User> = emptyList<User>(),
        val image: Uri? = null,
        var menuExpanded: Boolean = false,
        var membersFieldPosition: Size = Size.Zero
    ): AddGroupUiState()

    data class Loading (
        val name: String,
        val description: String,
        val members: MutableList<User>,
        val allUsers: List<User>,
        val image: Uri?,
        var menuExpanded: Boolean,
        var membersFieldPosition: Size
    ): AddGroupUiState()

    data class Error (
        val name: String,
        val description: String,
        val members: MutableList<User>,
        val allUsers: List<User>,
        val image: Uri?,
        val errorMessage: String,
        var menuExpanded: Boolean,
        var membersFieldPosition: Size
    ): AddGroupUiState()

    data class Success (
        val successMessage: String
    ): AddGroupUiState()
}